package com.pst.cars.models.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarXml {

    @JacksonXmlProperty(localName = "type")
    private String type;

    @JacksonXmlProperty(localName = "model")
    private String model;

    @JacksonXmlProperty(localName = "price")
    public Price price;

    @JacksonXmlElementWrapper(localName = "prices")
    private List<Price> prices;

}
