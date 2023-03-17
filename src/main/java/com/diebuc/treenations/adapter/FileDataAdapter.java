package com.diebuc.treenations.adapter;

import com.diebuc.treenations.model.CoTonesData;

import java.io.IOException;
import java.util.List;

/**
 * FileDataAdapter is an interface for reading data from different file formats
 * and returning a list of CoTonesData objects.
 */
public interface FileDataAdapter {

    /**
     * Reads data from a file and returns a list of CoTonesData objects.
     *
     * @param filePath The path to the file to be read.
     * @return A list of CoTonesData objects.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    List<CoTonesData> readData(String filePath) throws IOException;
}
