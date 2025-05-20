package com.pst.cars.repositories;

import com.pst.cars.models.Car;
import com.pst.cars.models.FilterRequest;

import java.util.List;

public interface CarRepositoryCustom {

    List<Car> searchCars (FilterRequest filter);

}
