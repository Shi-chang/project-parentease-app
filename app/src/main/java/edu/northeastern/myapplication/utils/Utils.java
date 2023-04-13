package edu.northeastern.myapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class Utils {
    /**
     * Gets the city name based on the location provided.
     * <p>
     * Reference: https://developer.android.com/reference/android/location/Geocoder
     *
     * @param context  the context
     * @param location the location
     * @return the city name
     */
    public static String getCityName(Context context, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(context, "Failed to get city name.", Toast.LENGTH_SHORT).show();
                return null;
            }
            Address address = addresses.get(0);
            String cityName = address.getLocality();
            return cityName;
        } catch (IOException e) {
            Toast.makeText(context, "Failed to get city name.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public static Properties getProperties(Context context) {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("firebase.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Compares two dates.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return 1, 0 or -1 if the first date is greater than, equal to, or smaller than the second date
     */
    public static int compareDates(Date date1, Date date2) {
        if (date1.getYear() >= date2.getYear() && date2.getMonth() >= date2.getMonth() && date1.getDate() > date2.getDate()) {
            return 1;
        } else if (date1.getYear() == date2.getYear() && date2.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Gets the greeting string based on the current hour.
     *
     * @param currentHour the current hour
     * @return the greeting string
     */
    public static String getGreetingString(int currentHour) {
        String message = "";
        if (currentHour >= 6 && currentHour < 12) {
            message = "Good Morning, ";
        } else if (currentHour >= 12 && currentHour < 18) {
            message = "Good Afternoon, ";
        } else if (currentHour >= 18 && currentHour < 22) {
            message = "Good Evening, ";
        } else {
            message = "Good Night, ";
        }

        return message;
    }
}
