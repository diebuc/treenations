package com.diebuc.treenations.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * DataAdapterFactory is responsible for providing the appropriate FileDataAdapter based on the file extension.
 */
@Configuration
public class DataAdapterFactory {

    private final FileDataAdapter csvDataAdapter;
    private final FileDataAdapter jsonDataAdapter;

    @Autowired
    public DataAdapterFactory(FileDataAdapter csvDataAdapter, FileDataAdapter jsonDataAdapter) {
        this.csvDataAdapter = csvDataAdapter;
        this.jsonDataAdapter = jsonDataAdapter;
    }

    /**
     * Returns the appropriate FileDataAdapter based on the file extension.
     *
     * @param filePath The path to the file.
     * @return The appropriate FileDataAdapter implementation for the file extension.
     * @throws IllegalArgumentException If the file extension is not supported.
     */
    public FileDataAdapter getAdapter(String filePath) {
        String fileExtension = getFileExtension(filePath);

        switch (fileExtension.toLowerCase()) {
            case "csv":
                return this.csvDataAdapter;
            case "json":
                return this.jsonDataAdapter;
            default:
                throw new IllegalArgumentException("Unsupported file format: " + fileExtension);
        }
    }

    /**
     * Extracts the file extension from the given file path.
     *
     * @param filePath The path to the file.
     * @return The file extension as a string.
     */
    private String getFileExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return lastIndexOfDot != -1 ? fileName.substring(lastIndexOfDot + 1) : "";
    }
}
