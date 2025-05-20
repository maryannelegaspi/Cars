package com.pst.cars.controllers;

import com.pst.cars.models.CarResponse;
import com.pst.cars.models.FilterRequest;
import com.pst.cars.services.CarService;
import com.pst.cars.services.output.OutputFormat;
import com.pst.cars.services.output.OutputService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cars inventory", description = "APIs for viewing Car inventory")
@RestController
@RequestMapping("/v1/cars")
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    private final OutputService outputService;

    @Operation(
            summary = "Retrieves cars uploaded in the system",
            description = "This API accepts a JSON @ModelAttribute " +
                    "which is used as a filter to get the available cars uploaded in the system."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CarResponse.class)
            )
    )
    @GetMapping("/search")
    public ResponseEntity<?> getCars (@ModelAttribute FilterRequest filter) {
        List<CarResponse> cars = carService.filterCars(filter);
        return ResponseEntity.ok(cars);
    }


    @Operation(
            summary = "Output cars uploaded in the system in JSON, XML and Table format",
            description = "This API accepts a JSON @ModelAttribute and a @RequestParam which is used as a filter " +
                    "to get the cars uploaded in the system. User can use different formats for the output type which are JSON, XML and Table Formats"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CarResponse.class)
            )
    )
    @GetMapping("/output")
    public ResponseEntity<?> output (
            @ModelAttribute FilterRequest filter,
            @RequestParam OutputFormat format
    ) {
        var cars = carService.filterCars(filter);
        var output = outputService.display(cars, format);
        return ResponseEntity.ok(output);
    }
}
