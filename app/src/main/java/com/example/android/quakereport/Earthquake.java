package com.example.android.quakereport;

/**
 * {@link Earthquake} represents a single earthquake at some location.
 * Each object has 3 properties: magnitude, place, and date.
 */

public class Earthquake {


    // Magnitude of the earthquake (i.e. 4.5)
    private String mMagnitude;

    // Location of the earthquake (i.e. San Francisco)
    private String mLocation;

    // Time of the earthquake
    private long mTimeInMilliseconds;

    /**
     * Create a new Earthquake object.
     * @param magnitude is the magnitude, or strength, of the earthquake
     * @param location is the where the earthquake happened
     * @param timeInMilliseconds is the time the earthquake occurred
     */
    public Earthquake(String magnitude, String location, long timeInMilliseconds) {
        mMagnitude = magnitude; //eMagnitude
        mLocation = location; //eCity
        mTimeInMilliseconds = timeInMilliseconds;
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
    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
}
