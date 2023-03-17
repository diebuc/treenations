package com.diebuc.treenations.adapter;

import com.diebuc.treenations.model.CoTonesData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * CsvDataAdapter is responsible for reading data from a CSV file and converting it into a list of CoTonesData objects.
 */
@Component("csvDataAdapter")
public class CsvDataAdapter implements FileDataAdapter {

    /**
     * Reads data from a CSV file and returns a list of CoTonesData objects.
     *
     * @param filePath The path to the CSV file.
     * @return A list of CoTonesData objects.
     * @throws IOException If there is an issue reading the file.
     */
    @Override
    public List<CoTonesData> readData(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath)) {
            CsvToBean<CoTonesData> csvToBean = new CsvToBeanBuilder<CoTonesData>(fileReader)
                    .withType(CoTonesData.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }
}
