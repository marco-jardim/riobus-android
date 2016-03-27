package com.tormentaLabs.riobus.marker.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by limazix on 12/09/15.
 */
public class MarkerUtils {

    /**
     * Used to create a basic marker
     * @param latitude
     * @param longitude
     * @return MarkerOptions
     */
    public static MarkerOptions createMarker(Double latitude, Double longitude) {
        LatLng position = new LatLng(latitude, longitude);
        return createMarker(position);
    }

    /**
     * Used to create a basic marker
     * @param position
     * @return MarkerOptions
     */
    public static MarkerOptions createMarker(LatLng position) {
        MarkerOptions options = new MarkerOptions();
        options.position(position);
        return options;
    }
}
