package com.diebuc.treenations.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileNameHelper is a utility class that provides methods for extracting information from file names.
 */
public class FileNameHelper {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("^(\\d{4})-COtonesInfo\\.(json|csv)$");

    /**
     * Extracts the year from the given file name.
     *
     * @param fileName The file name to extract the year from.
     * @return The year as a string if the file name matches the expected format.
     * @throws IllegalArgumentException If the file name format is invalid.
     */
    public static String getYearFromFileName(String fileName) {
        Matcher matcher = FILE_NAME_PATTERN.matcher(fileName);

        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Invalid file name format");
        }
    }
}
