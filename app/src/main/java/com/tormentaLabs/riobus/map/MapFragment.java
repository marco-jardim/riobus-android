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
import com.tormentaLabs.riobus.history.controller.HistoryController;
import com.tormentaLabs.riobus.map.bean.MapBuilder;
import com.tormentaLabs.riobus.map.listener.MapBuilderListener;
import com.tormentaLabs.riobus.map.utils.MapPrefs_;
import com.tormentaLabs.riobus.map.utils.MapUtils;
import com.tormentaLabs.riobus.map.view.LineMapControllerView;
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
public class MapFragment extends Fragment implements MapBuilderListener {

    private static final String TAG = MapFragment_.class.getName();
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @Pref
    MapPrefs_ mapPrefs;

    @Bean
    MapBuilder mapBuilder;

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

    @AfterViews
    public void afterViews() {
        lineMapControllerView.setVisibility(View.GONE);
        mapFragment = getMapFragment();
        map = mapFragment.getMap();
        setupMap();
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

        mapBuilder.setMap(map)
                .setListener(this)
                .centerOnUser();
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
        mapBuilder.centerOnUser();
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
                getActivity().setTitle(result);
                buildBusLineMap(result);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG, "search canceled");
            }
        }
    }

    // TODO do something when it is not valid
    private void buildBusLineMap(String keyword) {
        if (MapUtils.isValidString(keyword)) {
            setProgressVisibility(View.VISIBLE);
            mapBuilder.setQuery(keyword)
                    .setMap(map)
                    .buildMap();
        }
    }

    @Override
    public void onCenterComplete() {
        setProgressVisibility(View.GONE);
    }

    @UiThread
    @Override
    public void onMapBuilderComplete() {
        historyController.addLine(mapBuilder.getLine().number);
        lineMapControllerView.setVisibility(View.VISIBLE);
        lineMapControllerView.build(mapBuilder.getLine());
        setProgressVisibility(View.GONE);
    }

    @UiThread
    @Override
    public void onMapBuilderError(String errorMsg) {
        setProgressVisibility(View.GONE);
        Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_LONG).show();
    }
}