package com.example.android.quakereport;

/**
 * {@link Earthquake} represents a single earthquake at some location.
 * Each object has 3 properties: magnitude, place, and date.
 */

public class Earthquake {


    // Magnitude of the earthquake (i.e. 4.5)
    private String mMagnitude;

    // Location of the earthquake (i.e. San Francisco)
    private String mCity;

    // Date of the earthquake
    private String mDate;

    /**
     * Create a new Earthquake object.
     * @param magnitude is the magnitude, or strength, of the earthquake
     * @param location is the where the earthquake happened
     * @param time is when the earthquake occurred
     */
    public Earthquake(String magnitude, String location, String time) {
        mMagnitude = magnitude; //eMagnitude
        mCity = location; //eCity
        mDate = time; //eDate
    }

    /*
    Get the magnitude of the earthquake
     */
    public String getMagnitude() {
        return mMagnitude;
    }

    /*
    Get the location of the earthquake
     */
    public String getCity() {
        return mCity;
    }

    /*
    Get the date of the earthquake
     */
    public String getDate() {
        return mDate;
    }
}
