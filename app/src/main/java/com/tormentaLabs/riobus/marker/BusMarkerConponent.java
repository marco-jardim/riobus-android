package com.tormentaLabs.riobus.marker;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.map.bean.MapComponent;
import com.tormentaLabs.riobus.marker.model.BusModel;
import com.tormentaLabs.riobus.marker.service.BusService;
import com.tormentaLabs.riobus.marker.service.BusServiceErrorHandler;
import com.tormentaLabs.riobus.marker.utils.MarkerUtils;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.BackgroundExecutor;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class used to manage the buses of some given number to the map as a component.
 *
 * @author limazix
 * @since 2.0
 * Created on 02/09/15.
 */
@EBean
public class BusMarkerConponent extends MapComponent {

    private static final String TAG = BusMarkerConponent_.class.getName();
    private static final String BUSES_UPDATE_THREAD_ID = "auto_update";
    private static final String GET_BUSES_THREAD_ID = "get_buses";

    public static final int BUSES_UPDATE_INTERVAL = 15000;
    public static final int BOUNDS_PADDING = 50;

    private static final boolean INTERRUPT_IF_RUNNING = true;

    private boolean isAutoUpdate = false;
    private boolean ignoreSense = false;
    private LatLngBounds.Builder boundsBuilder;
    private List<Marker> markers = new ArrayList<>();
    private List<BusModel> buses;
    private List<LineModel> lines;

    @RestService
    BusService busService;

    @Bean
    LineController lineController;

    @Bean
    BusServiceErrorHandler busServiceErrorHandler;

    @AfterInject
    void afterInject() {
        busService.setRestErrorHandler(busServiceErrorHandler);
    }

    public List<LineModel> getLines() {
        return lines;
    }

    @Override
    public void prepareComponent() {
        isAutoUpdate = false;
        setIsBuildcomplete(false);
        setReverseSense(false);
        shutdownAutoUpdate();
        boundsBuilder = new LatLngBounds.Builder();
        getBusesByLine();
    }

    @Override
    public void buildComponent() {
        removeMarkers();
        addMarkers(buses);
        autoUpdateBusesPosition();
        if(!isBuildcomplete()) getListener().onComponentBuildComplete(TAG);
        setIsBuildcomplete(true);
    }

    @Override
    public void removeComponent() {
        shutdownAutoUpdate();
        removeMarkers();
    }

    @Background(delay = BUSES_UPDATE_INTERVAL, id = BUSES_UPDATE_THREAD_ID)
    void autoUpdateBusesPosition() {
        isAutoUpdate = true;
        getBusesByLine();
        removeMarkers();
        addMarkers(buses);
        autoUpdateBusesPosition();
    }

    /**
     * Used to aceess the server and get the list of buses of some given lie
     */
    @Background(id = GET_BUSES_THREAD_ID)
    void getBusesByLine() {
        buses = busService.getBusesByLine(getQuery());

        if (buses != null) {
            if(buses.isEmpty()) {
                String message = getContext().getResources().getString(R.string.no_bus_found);
                getListener().onComponentMapError(message, TAG);
                if(isAutoUpdate) shutdownAutoUpdate();
            } else storeLines();
        } else {
            String message = getContext().getResources().getString(R.string.error_connection_server);
            getListener().onComponentMapError(message, TAG);
        }

    }

    /**
     * Method used to store all lines found to DB
     */
    private void storeLines() {
        lines = new ArrayList<>();
        ArrayList<String> lineNumbers = new ArrayList<>();

        for(BusModel bus : buses) {
            if(!lineNumbers.contains(bus.getLine())) {
                LineModel line = lineController.createIfNotExists(bus.getLine());
                lines.add(line);
                lineNumbers.add(bus.getLine());
            }
        }

        if(lines.size() > 1) ignoreSense = true;
        if(!isAutoUpdate) getListener().onComponentMapReady(TAG);
    }

    /**
     * Used to add a marker for each buson map
     * @param buses List of buses
     */
    @UiThread
    void addMarkers(List<BusModel> buses) {
        for (BusModel bus : buses) {
            if(!isReverseSense() && bus.getSense().equals(getSense()) ||
                    isReverseSense() && !bus.getSense().equals(getSense()) ||
                    ignoreSense) {
                Marker marker = getMap().addMarker(getMarker(bus));
                markers.add(marker);
            }
        }
        if (!isAutoUpdate && !markers.isEmpty()) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), BOUNDS_PADDING);
            getMap().moveCamera(cameraUpdate);
        }
    }

    /**
     * Used to remove all markers of each bus from the map
     */
    @UiThread
    void removeMarkers() {
        for (Marker m : markers)
            m.remove();
        markers.clear();
    }

    /**
     * Used to shutdown threads of autoupdate proccess
     */
    private void shutdownAutoUpdate() {
        BackgroundExecutor.cancelAll(BUSES_UPDATE_THREAD_ID, INTERRUPT_IF_RUNNING);
        BackgroundExecutor.cancelAll(GET_BUSES_THREAD_ID, INTERRUPT_IF_RUNNING);
    }

    /**
     * Used to build the bus marker based on its features
     *
     * @param bus
     * @return MarkerOptions the bus marker
     */
    private MarkerOptions getMarker(BusModel bus) {
        MarkerOptions options = MarkerUtils.createMarker(bus.getLatitude(), bus.getLongitude());
        options.title(bus.getLine());
        options.icon(getIcon(bus.getTimeStamp()));
        options.snippet(bus.toString());
        boundsBuilder.include(options.getPosition());
        return options;
    }

    /**
     * Used to get the bus marker icon based on its last update position time
     *
     * @param timestamp Bus last update position time
     * @return BitmapDescriptor bus marker icon
     */
    private BitmapDescriptor getIcon(String timestamp) {
        BitmapDescriptor bitmap = null;
        try {
            Date date = RioBusUtils.parseStringToDateTime(timestamp);

            DateTime current = new DateTime(Calendar.getInstance());
            DateTime last = new DateTime(date);
            int diff = Minutes.minutesBetween(last, current).getMinutes();

            if (diff >= 5 && diff < 10) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.bus_yellow);
            } else if (diff >= 10) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.bus_red);
            } else {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.bus_green);
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }
}
