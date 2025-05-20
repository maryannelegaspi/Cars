package com.pst.cars.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CarResponse {

    @Schema(description = "Brand of the car", example = "NISSAN")
    private String brand;

    @Schema(description = "Car type", example = "SUV")
    private String type;

    @Schema(description = "Model of the Car", example = "Rogue")
    private String model;

    @Schema(description = "Model release date of the Car with format YYYY, DD, MM", example = "2023, 08, 22")
    private String releaseDate;

    @Schema(description = "Price object", example = "{ \"USD\": 22000.00 }")
    private Map<String, BigDecimal> price;
}
