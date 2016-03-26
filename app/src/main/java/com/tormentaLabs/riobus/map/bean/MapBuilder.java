package com.tormentaLabs.riobus.map.bean;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.itinerary.ItineraryComponent;
import com.tormentaLabs.riobus.map.listener.MapBuilderListener;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;
import com.tormentaLabs.riobus.marker.BusMarkerConponent;
import com.tormentaLabs.riobus.marker.UserMarkerComponent;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Class responsible by build and controll map components. </br>
 * It follows the Builder and Singleton patterns as structure
 * and an adaptation of Thread Pool pattern to manage the components creation.
 * @author limazix
 * @since 3.0.0
 * Created on 25/03/16
 */
@EBean(scope = EBean.Scope.Singleton)
public class MapBuilder implements MapComponentListener {

    private static final String TAG = MapBuilder.class.getName();
    private String query;
    private LineModel line;
    private GoogleMap map;
    private MapBuilderListener listener;

    @Bean
    UserMarkerComponent userMarkerComponent;

    @Bean
    BusMarkerConponent busMapComponent;

    @Bean
    ItineraryComponent itineraryComponent;

    /**
     * Method used to centralize the map on current user position
     */
    public void centerOnUser() {
        userMarkerComponent
                .setMap(map)
                .setListener(this)
                .buildComponent();
    }

    /**
     * Method to be called to start the construction process of the map's components
     */
    public void buildMap() {
        buildNextComponent(itineraryComponent);
    }

    private void buildNextComponent(MapComponent component) {
        if(line == null)
            component.setQuery(query);
        else
            component.setLine(line);

        component.setMap(map)
                    .setListener(this)
                    .buildComponent();
    }

    public GoogleMap getMap() {
        return map;
    }

    public MapBuilder setMap(GoogleMap map) {
        this.map = map;
        return this;
    }

    public LineModel getLine() {
        return line;
    }

    public MapBuilder setLine(LineModel line) {
        this.line = line;
        return this;
    }

    public MapBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public MapBuilderListener getListener() {
        return listener;
    }

    public MapBuilder setListener(MapBuilderListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onComponentMapReady(String componentId) {
        if(componentId.equals(itineraryComponent.getClass().getName())) {
            line = itineraryComponent.getLine();
            buildNextComponent(busMapComponent);
        } else if(componentId.equals(busMapComponent.getClass().getName())) {
            listener.onMapBuilderComplete();
        } else if(componentId.equals(userMarkerComponent.getClass().getName())) {
            listener.onCenterComplete();
        }
    }

    @Override
    public void onComponentMapError(String errorMsg, String componentId) {
        Log.e(TAG, componentId);
        listener.onMapBuilderError(errorMsg);
    }
}
