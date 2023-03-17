package com.diebuc.treenations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;

/**
 * CoTonesData is a class representing the data of CO2 tones compensated and the expected minimum.
 * It holds the information about the year, month, minimum expected compensation, and the reached compensation.
 */
public class CoTonesData {

    private String year;
    @CsvBindByName(column = "Month")
    @JsonProperty("Month")
    private String month;
    private Integer minimumExpected;

    @CsvBindByName(column = "Cot compensation")
    @JsonProperty("COt-compensation")
    private Integer coTonesCompensationReached;

    /**
     * Default constructor for CoTonesData.
     */
    public CoTonesData() {
    }

    /**
     * Constructs a new instance of CoTonesData with the specified year, month, minimum expected compensation,
     * and reached compensation.
     *
     * @param year                    The year of the data.
     * @param month                   The month of the data.
     * @param minimumExpected         The minimum expected compensation.
     * @param coTonesCompensationReached The reached compensation.
     */
    public CoTonesData(String year, String month, Integer minimumExpected, Integer coTonesCompensationReached) {
        this.year = year;
        this.month = month;
        this.minimumExpected = minimumExpected;
        this.coTonesCompensationReached = coTonesCompensationReached;
    }

    // Getters and setters for the class fields

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMinimumExpected() {
        return minimumExpected;
    }

    public void setMinimumExpected(int minimumExpected) {
        this.minimumExpected = minimumExpected;
    }

    public Integer getCoTonesCompensationReached() {
        return coTonesCompensationReached;
    }

    public void setCoTonesCompensationReached(int coTonesCompensationReached) {
        this.coTonesCompensationReached = coTonesCompensationReached;
    }
}
