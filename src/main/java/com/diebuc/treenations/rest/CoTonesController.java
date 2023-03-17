package com.diebuc.treenations.rest;

import com.diebuc.treenations.adapter.DataAdapterFactory;
import com.diebuc.treenations.model.CoTonesData;
import com.diebuc.treenations.service.CoTonesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CoTonesController is a REST controller that handles requests related to CO2 tones compensation data.
 */
@RestController
public class CoTonesController {

    DataAdapterFactory dataAdapterFactory;

    /**
     * Constructs a new CoTonesController with the specified data adapter factory.
     *
     * @param dataAdapterFactory The data adapter factory to be used.
     */
    public CoTonesController(DataAdapterFactory dataAdapterFactory) {
        this.dataAdapterFactory = dataAdapterFactory;
    }

    /**
     * Handles a POST request to "/lowImpactMonths" and returns a list of CoTonesData instances representing low impact months.
     *
     * @param file                      The uploaded file containing CO2 tones compensation data.
     * @param minimumCOTonesCompensation The minimum CO2 tones compensation value for filtering low impact months.
     * @return A ResponseEntity containing the list of low impact months, or an error response if an exception occurs.
     */
    @PostMapping("/lowImpactMonths")
    public ResponseEntity<List<CoTonesData>> getLowImpactMonths(
            @RequestParam("file") MultipartFile file,
            @RequestParam("minimumCOTonesCompensation") int minimumCOTonesCompensation) {

        try {
            Path tempFile = Files.createTempFile("temp-", "." + getFileExtension(file.getOriginalFilename()));
            Files.write(tempFile, file.getBytes());

            String year = file.getOriginalFilename().substring(0, 4);

            var coTonesService = new CoTonesService(dataAdapterFactory.getAdapter(tempFile.toString()));

            List<CoTonesData> lowImpactMonths = coTonesService.getLowImpactMonths(tempFile.toString(), year, minimumCOTonesCompensation);
            Files.deleteIfExists(tempFile);

            return ResponseEntity.ok(lowImpactMonths);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves the file extension from the given file name.
     *
     * @param fileName The file name to retrieve the extension from.
     * @return The file extension as a string, or an empty string if no extension is found.
     */
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return lastIndexOfDot != -1 ? fileName.substring(lastIndexOfDot + 1) : "";
    }
}
