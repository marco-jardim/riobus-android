package com.tormentaLabs.riobus.marker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.tormentaLabs.riobus.marker.model.BusModel;
import com.tormentaLabs.riobus.marker.views.BusInfoWindowView;
import com.tormentaLabs.riobus.marker.views.BusInfoWindowView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;

@EBean
public class BusInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private BusInfoWindowView busInfoWindowView;

    @RootContext
    Context context;

    private static final String TAG = BusInfoWindowAdapter.class.getName();

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        try {
            BusModel bus = new ObjectMapper().readValue(marker.getSnippet(), BusModel.class);
            busInfoWindowView = BusInfoWindowView_.build(context);
            busInfoWindowView.bind(bus);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return busInfoWindowView;
    }

}
