package com.pst.cars.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Entity
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private LocalDate yearModel;
    private String model;
    private String type;

    @ElementCollection
    @CollectionTable(name = "car_prices", joinColumns = @JoinColumn(name = "car_id"))
    @MapKeyColumn(name="price_key")
    @Column(name="price_value")
    @XmlElementWrapper(name = "prices")
    @XmlElement(name = "price")
    private Map<String, BigDecimal> price;

}
