package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
@SuppressWarnings("ALL")
public final class QueryUtils {

    /** Tag for log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /** Query USGS dataset and return list of {@link Earthquake} objects. */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        URL url = createUrl(requestUrl);

        // Perform HTTP request to URL and receive JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request.", e);
        }

        // Extract relevant fields from JSON response and create list of {@link Earthquake}s
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        // Return list of {@link Earthquake}s
        return earthquakes;

    }


    /** Returns new URL object from given string URL. */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /** Make HTTP request to given URL and return String response. */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If URL is null, return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If request was successful (response code 200),
            // then read input stream and parse response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing input stream could throw IOException, which is why the
                // makeHttpRequest(URL url) method signature specifies that
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /** Convert the {@link InputStream} into a String containing the whole JSON response */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returning a list of the {@link Earthquake} objects that have
     * beeen built up from parsing the given JSON response.
     */
    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        // If JSON string's empty or null return early
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty ArrayList we can start adding quakes to
        List<Earthquake> earthquakes = new ArrayList<>();


        /**
         * Try to parse the JSON response string. If there's a problem with the way JSON
         * is formatted, a JSON exception object will be thrown.
         * Catch the exception so the app doesn't crash & print error to logs
         */

            try {
                // Create JSONObject from response string
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

                // Extract JSONArray associated with key called "features",
                // which represents a list of features (or earthquakes).
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

                // For each quake in the earthquakeArray, create an {@link Earthquake} object
                for (int i = 0; i < earthquakeArray.length(); i++) {

                    // Get a single earthquake at position i within the list of quakes
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                    // For a given earthquake, extract the JSONObject associated with the
                    // key called "properties", which represents a list of all properties
                    // for that earthquake.
                    JSONObject properties = currentEarthquake.getJSONObject("properties");

                    // Extract value for key "mag"
                    double magnitude = properties.getDouble("mag");

                    // Extract value for key "place"
                    String location = properties.getString("place");

                    // Extract value for key "time"
                    long time = properties.getLong("time");

                    // Extract value for key "url"
                    String url = properties.getString("url");

                    // Create new {@link Earthquake} object with mag, location, time,
                    // and url from JSON response.
                    Earthquake earthquake = new Earthquake(magnitude, location, time, url);

                    // Add new {@link Earthquake} to list of earthquakes.
                    earthquakes.add(earthquake);
                }
            } catch (JSONException e) {
                // If an error is thrown when executing any of the statements above, catch it
                // here so the app doesn't crash. Print it to the logs with the message.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }
            // Return the list of earthquakes
            return earthquakes;
    }
}

