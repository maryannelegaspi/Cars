package com.pst.cars.services.load;


import com.pst.cars.exceptions.GenericException;
import com.pst.cars.models.Car;
import com.pst.cars.models.csv.BrandModel;
import com.pst.cars.models.xml.CarXml;
import com.pst.cars.models.xml.CarsXml;
import com.pst.cars.models.xml.Price;
import com.pst.cars.repositories.CarRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarDataLoaderServiceImpl {

    private static final Map<String, String> MODEL_BRAND_MAPPING = new HashMap<>();

    static {
        MODEL_BRAND_MAPPING.put("RAV4", "Toyota");
        MODEL_BRAND_MAPPING.put("Civic", "Honda");
        MODEL_BRAND_MAPPING.put("F-150", "Ford");
        MODEL_BRAND_MAPPING.put("Model X", "Tesla");
        MODEL_BRAND_MAPPING.put("330i", "BMW");
        MODEL_BRAND_MAPPING.put("Q5", "Audi");
        MODEL_BRAND_MAPPING.put("Silverio", "Chevrolet");
        MODEL_BRAND_MAPPING.put("C-Class", "Mercedes-Benz");
        MODEL_BRAND_MAPPING.put("Rogue", "Nissan");
        MODEL_BRAND_MAPPING.put("Elantra", "Hyundai");
    }

    private final XmlReader xmlReader;
    private final CsvReader csvReader;
    private final CarRepository carRepository;

    // Loads car data after bean creation
    @PostConstruct
    public void loadData() {
        try {

            CarsXml carsXml = xmlReader.read();
            List<BrandModel> brandModels = csvReader.read();

            final Map<String, BrandModel> brandMap = brandModels.parallelStream()
                    .collect(Collectors.toMap(BrandModel::getBrand, Function.identity()));

            carRepository.saveAll(buildCar(carsXml, brandMap));

        } catch (FileNotFoundException e) {
            throw GenericException.internalServerError();
        }
    }

    private List<Car> buildCar (CarsXml carsXml, Map<String, BrandModel> brandMap) {
        List<Car> cars = new ArrayList<>();
        for (CarXml carXml : carsXml.getCars()) {
            String modelFromStatic = MODEL_BRAND_MAPPING.get(carXml.getModel());
            if (Objects.isNull(modelFromStatic)) {
                continue;
            }
            BrandModel brandModelFromCsv = brandMap.get(modelFromStatic);
            if (Objects.nonNull(brandModelFromCsv)) {
                Car newCar = buildCar(carXml, brandModelFromCsv);
                cars.add(newCar);
            }
        }
        return cars;
    }

    private Car buildCar (CarXml carXml, BrandModel brandModelFromCsv) {
        Car car = new Car();
        car.setBrand(brandModelFromCsv.getBrand());
        car.setYearModel(brandModelFromCsv.getReleaseDate());
        car.setModel(carXml.getModel());
        car.setType(carXml.getType());

        Map<String, BigDecimal> prices = carXml.getPrices()
                .stream()
                .collect(Collectors.toMap(Price::getCurrency, Price::getAmount));
        prices.put(carXml.getPrice().getCurrency(), carXml.getPrice().getAmount());
        car.setPrice(prices);
        return car;
    }

}
