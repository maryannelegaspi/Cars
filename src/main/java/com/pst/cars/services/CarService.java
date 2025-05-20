package com.pst.cars.services;

import com.pst.cars.models.CarResponse;
import com.pst.cars.models.FilterRequest;

import java.util.List;

public interface CarService {
    List<CarResponse> filterCars(FilterRequest filter);
}
