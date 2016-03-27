package com.tormentaLabs.riobus.map.listener;

/**
 * @author limazix
 * @since 3.0.0
 * Created on 25/03/16
 */
public interface MapBuilderListener {
    public void onCenterComplete();
    public void onMapBuilderComplete();
    public void onMapBuilderError(String errorMsg);
}
