package com.pst.cars.services.load;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.pst.cars.exceptions.GenericException;
import com.pst.cars.models.csv.BrandModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Component
public class CsvReader {

    public List<BrandModel> read () throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("brand/CarsBrand.csv");
        if (Objects.isNull(inputStream)) {
            throw new FileNotFoundException("File NOT found in the resources location: brand/CarsBrand.csv");
        }

        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();

            MappingIterator<BrandModel> brandModel = csvMapper
                    .readerFor(BrandModel.class)
                    .with(csvSchema)
                    .readValues(inputStream);
            return brandModel.readAll();
        } catch (IOException e) {
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An error occurred. Contact administrator for further assistance");
        }
    }

}
