package com.diebuc.treenations.service;

import com.diebuc.treenations.model.CoTonesData;
import com.diebuc.treenations.adapter.FileDataAdapter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CoTonesService is a service class that provides methods for processing CO2 tones compensation data.
 */
public class CoTonesService {

    private final FileDataAdapter adapter;

    /**
     * Constructs a new CoTonesService with the specified file data adapter.
     *
     * @param adapter The file data adapter to be used.
     */
    public CoTonesService(FileDataAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Retrieves a list of low impact months from the given file path based on the specified year and minimum CO2 tones compensation.
     *
     * @param filePath                 The path to the input file containing CO2 tones compensation data.
     * @param year                     The year to be set in the CoTonesData instances.
     * @param minimumCOTonesCompensation The minimum CO2 tones compensation value for filtering low impact months.
     * @return A list of CoTonesData instances representing low impact months.
     * @throws IOException If an I/O error occurs while reading data from the file.
     */
    public List<CoTonesData> getLowImpactMonths(String filePath, String year, Integer minimumCOTonesCompensation) throws IOException {
        List<CoTonesData> coTonesDataList = adapter.readData(filePath);
        coTonesDataList.forEach(data -> {
            data.setYear(year);
            data.setMinimumExpected(minimumCOTonesCompensation);
        });

        return coTonesDataList.stream()
                .filter(data -> data.getMinimumExpected() > data.getCoTonesCompensationReached())
                .collect(Collectors.toList());
    }
}
