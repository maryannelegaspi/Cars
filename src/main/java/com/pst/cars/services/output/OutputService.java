package com.pst.cars.services.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pst.cars.exceptions.GenericException;
import com.pst.cars.models.CarResponse;
import com.pst.cars.models.Cars;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OutputService {

    private final ObjectMapper OBJECT_MAPPER;
    private final XmlMapper XML_MAPPER = new XmlMapper();

    public Object display(List<CarResponse> carResponses, OutputFormat format) {
        return switch (format) {
            case TABULAR -> displayInTabular(carResponses);
            case XML -> displayInXml(carResponses);
            case JSON -> displayInJson(carResponses);
        };
    }

    public String displayInTabular(List<CarResponse> carResponses) {
        StringBuilder result = new StringBuilder(String.format("%-15s | %-10s | %-15s | %-15s | %-30s%n",
                "Brand", "Type", "Model", "Release Date", "Price") +
                "-------------------------------------------------------------------------------------\n");

        for (CarResponse car : carResponses) {
            result.append(String.format("%-15s | %-10s | %-15s | %-15s | %-30s%n",
                    car.getBrand(),
                    car.getType(),
                    car.getModel(),
                    car.getReleaseDate(),
                    car.getPrice().toString()));
        }

        return result.toString();
    }

    public String displayInJson(List<CarResponse> carResponses) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(carResponses);
        } catch (JsonProcessingException e) {
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Encountered a problem when trying to deserialize Object to JSON");
        }
    }

    public String displayInXml(List<CarResponse> carResponses) {
        try {
            Cars wrapper = new Cars(carResponses);
            return XML_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);
        } catch (JsonProcessingException e) {
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Encountered a problem when trying to deserialize Object to XML");
        }
    }
}
