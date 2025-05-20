package com.pst.cars.adapter;

import com.pst.cars.models.Car;
import com.pst.cars.models.CarResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
public class CarResponseAdapter {

    public CarResponse transform (Car car, Map<String, BigDecimal> priceMap) {

        CarResponse response = new CarResponse();

        response.setBrand(car.getBrand());
        response.setModel(car.getModel());
        response.setType(car.getType());
        response.setPrice(car.getPrice());

        Optional.ofNullable(priceMap)
                .ifPresent(response::setPrice);
        Optional.ofNullable(car.getYearModel())
                .ifPresent(ym -> response.setReleaseDate(ym.toString()));

        return response;
    }
}
