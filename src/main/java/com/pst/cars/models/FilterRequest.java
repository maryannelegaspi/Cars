package com.pst.cars.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FilterRequest {

    @Schema(description = "Brand to filter", example = "Nissan")
    private String brand;

    @Schema(description = "Release date to filter", example = "2023-08-22")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @Schema(description = "Price to filter. Results will display greater than or equal to given value", example = "100")
    private BigDecimal price;

    @Schema(description = "Currency to filter", example = "USD")
    private String currency;

    // Sorting
    @Schema(description = "Column to sort", example = "brand")
    private String sort;
    private Order order;
}
