package com.example.android.quakereport;

/**
 * {@link Earthquake} represents a single earthquake at some location.
 * Each object has 3 properties: magnitude, place, and date.
 */

@SuppressWarnings("ALL")
public class Earthquake {


    // Magnitude of the earthquake (i.e. 4.5)
    private double mMagnitude;

    // Location of the earthquake (i.e. San Francisco)
    private String mLocation;

    // Time of the earthquake
    private long mTimeInMilliseconds;

    // Get URL
    private String mUrl;

    /**
     * Create a new Earthquake object.
     * @param magnitude is the magnitude, or strength, of the earthquake
     * @param location is the where the earthquake happened
     * @param timeInMilliseconds is the time the earthquake occurred
     * @param url is the website URL to find more details about each earthquake
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    /*
    Get the magnitude of the earthquake
     */
    public double getMagnitude() {
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

    /*
    Returns website URL to find more info about quakes
     */
    public String getUrl() {
        return mUrl;
    }
}
