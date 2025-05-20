package com.pst.cars.services;

import com.pst.cars.adapter.CarResponseAdapter;
import com.pst.cars.models.Car;
import com.pst.cars.models.CarResponse;
import com.pst.cars.models.FilterRequest;
import com.pst.cars.repositories.CarRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private CarResponseAdapter adapter;

    @Override
    public List<CarResponse> filterCars (FilterRequest filter) {

        List<Car> cars = carRepository.searchCars(filter);
        List<CarResponse> carResponses = new ArrayList<>(cars.size());

        // ArrayList has insertion order, since we are just iterating and inserting, sorting will be retained
        for (Car car : cars) {

            var filteredPrice = filterPrice(car, filter);

            if (filteredPrice.isEmpty()) continue;

            CarResponse response = adapter.transform(car, filteredPrice);
            carResponses.add(response);
        }

        return carResponses;
    }

    private Map<String, BigDecimal> filterPrice (Car car, FilterRequest filterRequest) {
        return car.getPrice()
                .entrySet()
                .stream()
                .filter(e -> isEntryMatchFilter(e, filterRequest))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isEntryMatchFilter (Map.Entry<String, BigDecimal> priceEntry, FilterRequest filter) {

        var currencyOptional = Optional.ofNullable(filter.getCurrency())
                .filter(StringUtils::isNotBlank);
        var priceOptional = Optional.ofNullable(filter.getPrice());

        // If there is no filter, return true early
        if (currencyOptional.isEmpty() && priceOptional.isEmpty()) {
            return true;
        }

        var currencyPredicate = currencyOptional
                .map(c -> Objects.equals(c, priceEntry.getKey()))
                .orElse(true);
        var pricePredicate = priceOptional
                // Filter entries that equal or greater than the criteria
                .map(p -> priceEntry.getValue().compareTo(p) >= 1)
                .orElse(true);

        return currencyPredicate && pricePredicate;
    }

}
