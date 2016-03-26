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
import com.tormentaLabs.riobus.marker.views.UserInfoWindowView;
import com.tormentaLabs.riobus.marker.views.UserInfoWindowView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;

@EBean
public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = MarkerInfoWindowAdapter.class.getName();
    private View markerInfoWindowView;

    @RootContext
    Context context;

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if(marker.getTitle() == null) // TODO find a better way to define the marker type
            markerInfoWindowView = getUserInfoWindowView();
        else
            markerInfoWindowView = getBusInfoWindowView(marker);

        return markerInfoWindowView;
    }

    private View getUserInfoWindowView() {
        UserInfoWindowView userInfoWindowView = UserInfoWindowView_.build(context);
        userInfoWindowView.bind();
        return userInfoWindowView;
    }

    private View getBusInfoWindowView(Marker marker) {
        BusInfoWindowView busInfoWindowView = null;
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