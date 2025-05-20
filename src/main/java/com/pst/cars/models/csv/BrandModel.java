package com.pst.cars.models.csv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class BrandModel {

    @JsonProperty("Brand")
    private String brand;

    @JsonProperty("ReleaseDate")
    private String releaseDateRaw;

    public LocalDate getReleaseDate() {
        return LocalDate.parse(releaseDateRaw, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @Override
    public String toString() {
        return brand + " released on " + getReleaseDate();
    }

}
