package com.tormentaLabs.riobus.map.listener;

/**
 * Created by limazix on 01/09/15.
 */
public interface MapComponentListener {
    /**
     * @param componentId component class name
     */
    public void onComponentMapReady(String componentId);

    /**
     * @param componentId component class name
     */
    public void onComponentBuildComplete(String componentId);

    /**
     * @param errorMsg
     * @param componentId component class name
     */
    public void onComponentMapError(String errorMsg, String componentId);
}
