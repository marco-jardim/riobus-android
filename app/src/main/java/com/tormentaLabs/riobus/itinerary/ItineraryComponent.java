package com.tormentaLabs.riobus.itinerary;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.itinerary.model.ItineraryModel;
import com.tormentaLabs.riobus.itinerary.model.SpotModel;
import com.tormentaLabs.riobus.itinerary.service.ItineraryService;
import com.tormentaLabs.riobus.itinerary.service.ItineraryServiceErrorHandler;
import com.tormentaLabs.riobus.map.bean.MapComponent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

/**
 * Class used to manage the itinarary of some given number to the map as a component.
 * @author limazix
 * @since 2.1
 * Created on 05/09/15.
 */
@EBean
public class ItineraryComponent extends MapComponent {

    private static final String TAG = ItineraryComponent_.class.getName();
    private static final float LINE_WIDTH = 12;
    private Polyline polyline = null;

    @RestService
    ItineraryService itineraryService;

    @Bean
    LineController lineController;

    @Bean
    ItineraryServiceErrorHandler itineraryServiceErrorHandler;

    @AfterInject
    void afterInject() {
        itineraryService.setRestErrorHandler(itineraryServiceErrorHandler);
    }

    @Background
    void getItineraries(){
        ItineraryModel itinerary = itineraryService.getItinarary(getQuery());

        if(itinerary == null)
            return;

        LineModel line = lineController.createIfNotExists(itinerary.getLine());
        line.description = itinerary.getDescription();
        line.save();
        setLine(line);

        ArrayList<LatLng> spots = new ArrayList<>();
        for(SpotModel spot : itinerary.getSpots()) {
            LatLng position = new LatLng(spot.getLatitude(), spot.getLongitude());
            spots.add(position);
        }
        drawItinerary(spots);
        getListener().onComponentMapReady(TAG);
    }

    @UiThread
    void drawItinerary(ArrayList<LatLng> spots) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(spots);
        polylineOptions.width(LINE_WIDTH);
        polylineOptions.color(getContext().getResources().getColor(R.color.riobus_primary_dark));
        polyline = getMap().addPolyline(polylineOptions);
    }

    @Override
    public void buildComponent() {
        removeComponent();
        getItineraries();
    }

    @Override
    public void removeComponent() {
        if(polyline != null && polyline.isVisible())
            polyline.remove();
    }
}
