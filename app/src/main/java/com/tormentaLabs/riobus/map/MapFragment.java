package com.tormentaLabs.riobus.map;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.history.controller.HistoryController;
import com.tormentaLabs.riobus.itinerary.ItineraryComponent;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;
import com.tormentaLabs.riobus.map.utils.MapPrefs_;
import com.tormentaLabs.riobus.map.utils.MapUtils;
import com.tormentaLabs.riobus.map.view.LineMapControllerView;
import com.tormentaLabs.riobus.marker.BusMarkerComponent;
import com.tormentaLabs.riobus.marker.UserMarkerComponent;
import com.tormentaLabs.riobus.search.SearchActivity_;
import com.tormentaLabs.riobus.search.utils.SearchUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author limazix
 * @since 2.0
 * Created on 01/09/15
 */
@OptionsMenu(R.menu.map_fragment)
@EFragment(R.layout.fragment_map)
public class MapFragment extends Fragment implements MapComponentListener {

    private static final String TAG = MapFragment_.class.getName();
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @Pref
    MapPrefs_ mapPrefs;

    @Bean
    BusMarkerComponent busMapComponent;

    @Bean
    UserMarkerComponent userMarkerComponent;

    @Bean
    ItineraryComponent itineraryComponent;

    @Bean
    HistoryController historyController;

    @Bean
    LineController lineCtrl;

    @ViewById(R.id.rioBusProgressBar)
    ProgressBar progressBar;

    @ViewById(R.id.lineMapControllerView)
    LineMapControllerView lineMapControllerView;

    @OptionsMenuItem(R.id.search)
    MenuItem menuSearch;
    private LineModel line;

    @AfterViews
    public void afterViews() {
        lineMapControllerView.setVisibility(View.GONE);
        mapFragment = getMapFragment();
        map = mapFragment.getMap();
        setupMap();

        userMarkerComponent.setMap(map)
                .setListener(this)
                .buildComponent();
    }

    /**
     * Used to setup global map preferences
     */
    private void setupMap() {
        if(map.getUiSettings() != null) {
            map.getUiSettings().setMapToolbarEnabled(mapPrefs.isMapToolbarEnable().get());
            map.getUiSettings().setCompassEnabled(mapPrefs.isMapCompasEnable().get());
            map.setTrafficEnabled(mapPrefs.isMapTrafficEnable().get());
        }
        map.setMyLocationEnabled(mapPrefs.isMapMyLocationEnable().get());
    }

    /**
     * Used to access the map fragment which is a child fragment called map_fragment
     * @return SupportMapFragment
     */
    private SupportMapFragment getMapFragment() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
    }

    @UiThread
    void setProgressVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Click(R.id.userPositionButton)
    void updateUserLocation() {
        userMarkerComponent.updateUserLocation();
    }

    @OptionsItem(R.id.search)
    boolean menuSearch() {
        Intent i = new Intent(getActivity(), SearchActivity_.class);
        startActivityForResult(i, Activity.DEFAULT_KEYS_SEARCH_GLOBAL);
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.DEFAULT_KEYS_SEARCH_GLOBAL) {
            if(resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(SearchUtils.EXTRA_SEARCH_RESULT);
                buildBusLineMap(result);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG, "search canceled");
            }
        }
    }

    private void buildLineMapControllerView() {
    }

    @UiThread
    @Override
    public void onComponentMapReady(String componentId) {
        //TODO workaround
        if(line != null && line.description != null) {
            lineMapControllerView.setVisibility(View.VISIBLE);
            lineMapControllerView.build(line);
        }
        setProgressVisibility(View.GONE);
    }

    @UiThread
    @Override
    public void onComponentMapError(String errorMsg, String componentId) {
        setProgressVisibility(View.GONE);
        Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_LONG).show();
    }

    // TODO do something when it is not valid
    private void buildBusLineMap(String keyword) {
        if (MapUtils.isValidString(keyword)) {
            line = historyController.addLine(keyword);
            busMapComponent.setLine(line)
                    .setListener(this)
                    .setMap(map)
                    .buildComponent();

            itineraryComponent.setLine(line)
                    .setListener(this)
                    .setMap(map)
                    .buildComponent();
        }
    }

}