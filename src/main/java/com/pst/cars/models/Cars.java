package com.pst.cars.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "cars")
public class Cars {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CarResponse> car;

    public Cars() {}
    public Cars(List<CarResponse> car) { this.car = car; }

}
