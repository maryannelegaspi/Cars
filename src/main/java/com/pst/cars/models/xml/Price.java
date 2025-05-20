package com.pst.cars.models.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Price {

    @JacksonXmlProperty(isAttribute = true)
    public String currency;

    @JacksonXmlText
    public BigDecimal amount;
}
