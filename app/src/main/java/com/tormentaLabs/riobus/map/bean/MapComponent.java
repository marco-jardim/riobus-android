package com.tormentaLabs.riobus.map.bean;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.map.listener.MapComponentListener;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Used as basic structure for any map component.
 * @author limazix
 * @since 2.0
 * Created on 12/09/15.
 */
@EBean
public abstract class MapComponent {

    @RootContext
    public Context context;

    private String query;
    private LineModel line;
    private MapComponentListener listener;
    private GoogleMap map;

    public Context getContext() {
        return context;
    }

    public String getQuery() {
        return query;
    }

    public MapComponent setQuery(String query) {
        this.query = query;
        return this;
    }

    public LineModel getLine() {
        return line;
    }

    public MapComponent setLine(LineModel line) {
        this.line = line;
        return this;
    }

    public MapComponentListener getListener() {
        return listener;
    }

    public MapComponent setListener(MapComponentListener listener) {
        this.listener = listener;
        return this;
    }

    public GoogleMap getMap() {
        return map;
    }

    public MapComponent setMap(GoogleMap map) {
        this.map = map;
        return this;
    }

    public abstract void buildComponent();

    public abstract void removeComponent();

}
