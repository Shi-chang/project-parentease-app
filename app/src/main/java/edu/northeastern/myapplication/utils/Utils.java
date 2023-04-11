package edu.northeastern.myapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
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
}
