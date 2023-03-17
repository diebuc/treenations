package com.diebuc.treenations.adapter;

import com.diebuc.treenations.model.CoTonesData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * JsonDataAdapter is a class that implements the FileDataAdapter interface
 * to read data from JSON files and convert them into a list of CoTonesData objects.
 */
@Component("jsonDataAdapter")
public class JsonDataAdapter implements FileDataAdapter {

    /**
     * Reads data from a JSON file and returns a list of CoTonesData objects.
     *
     * @param filePath The path to the JSON file to be read.
     * @return A list of CoTonesData objects.
     * @throws IOException If an I/O error occurs while reading the file or if the file has an invalid JSON format.
     */
    @Override
    public List<CoTonesData> readData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode rootNode = objectMapper.readTree(Paths.get(filePath).toFile());
        JsonNode dataArrayNode = rootNode.get("annual-trees-info");

        if (dataArrayNode != null && dataArrayNode.isArray()) {
            return objectMapper.convertValue(dataArrayNode, new TypeReference<List<CoTonesData>>() {});
        } else {
            throw new IOException("Invalid JSON format");
        }
    }
}
