package com.tormentaLabs.riobus.marker;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.tormentaLabs.riobus.map.bean.MapComponent;
import com.tormentaLabs.riobus.marker.utils.MarkerUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Class used to manage the user position on the map as a component.
 * @author limazix
 * @since 2.0
 * Created on 02/09/15.
 */
@EBean
public class UserMarkerComponent extends MapComponent implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static String TAG = UserMarkerComponent_.class.getName();
    private GoogleApiClient googleApiClient;
    private Marker userMarker;
    private Boolean isApiReady = false;

    private void setupGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @UiThread
    public void updateUserLocation() {
        if(!isApiReady) {
            setupGoogleApiClient();
            return;
        }

        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation == null) return;

        LatLng position = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
        getMap().animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

        if(userMarker != null) userMarker.remove();
        userMarker = getMap().addMarker(MarkerUtils.createMarker(position));

        getListener().onComponentMapReady(TAG);
    }

    @Override
    public void prepareComponent() {

    }

    @Override
    public void buildComponent() {
         setupGoogleApiClient();
    }

    @Override
    public void removeComponent() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        updateUserLocation();
        isApiReady = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        isApiReady = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, connectionResult.toString());
        isApiReady = false;
    }
}
