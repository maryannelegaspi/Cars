package com.pst.cars.services.load;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pst.cars.exceptions.GenericException;
import com.pst.cars.models.xml.CarsXml;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Component
public class XmlReader {

    public CarsXml read () throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("type/carsType.xml");
        if (Objects.isNull(inputStream)) {
            throw new FileNotFoundException("File NOT found in the resources location: type/carsType.xml");
        }

        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(inputStream, CarsXml.class);
        } catch (IOException e) {
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An error occurred. Contact administrator for further assistance");
        }
    }
}
