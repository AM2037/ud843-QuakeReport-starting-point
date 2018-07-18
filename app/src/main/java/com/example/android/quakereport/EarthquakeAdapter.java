package com.example.android.quakereport;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

/*
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Earthquake} objects.
 * */
@SuppressWarnings("ALL")
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private String primaryLocation;
    private String locationOffset;
    private static final String LOCATION_SEPARATOR = " of ";


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *  @param context        The current context. Used to inflate the layout file.
     * @param earthquakes A List of AndroidFlavor objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        assert currentEarthquake != null;
        String initialLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;

        if (initialLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = initialLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.offset_alternative);
            primaryLocation = initialLocation;
        }

        // Find the TextView in the list_item.xml layout with the ID magnitude
        TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        //Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(formattedMagnitude);


        // Find the TextView in the list_item.xml layout with the ID location
        TextView locationView = listItemView.findViewById(R.id.primary_location);
        // Get the location from the current Earthquake object
        locationView.setText(primaryLocation);

        // Find the TextView in the list_item.xml layout with the ID offset_location
        TextView offsetView = listItemView.findViewById(R.id.offset_location);
        // Get the offset location from the current Earthquake object
        offsetView.setText(locationOffset);

        // Create a new Date object from the time in ms of the earthquake
        Date dateObject;
        dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView with the ID date
        TextView dateView = listItemView.findViewById(R.id.date);
        // Format date string (i.e. "March 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeTextView = listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "3:00PM")
        String formattedTime = formatTime(dateObject);
        //Display the time of the current earthquake in that TextView
        timeTextView.setText(formattedTime);

        // Set the proper background color for mag circle
        // Get background from TextView which is a GradientDrawable
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get appropriate background color based on current magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set color on magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the magnitude circle's color based on the strength of the quake
     */
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted date string (i.e. "March 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted time string (i.e. "4:30PM") from a Date object.
     */
    private String formatTime(Date dateObject){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude double showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value
     */
    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}