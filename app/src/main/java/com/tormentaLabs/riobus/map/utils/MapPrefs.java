package com.tormentaLabs.riobus.map.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Used to store/access googlemap module preferences
 * @author limazix
 * @since 2.0
 * Created at 01/09/15
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface MapPrefs {

    /**
     * Used to enable/disable the Google Map Toolbar feature
     * @return Boolean
     */
    @DefaultBoolean(value = false)
    boolean isMapToolbarEnable();

    /**
     * Used to enable/disable the user ability to set his location at Google Map
     * @return Boolean
     */
    @DefaultBoolean(value = false)
    boolean isMapMyLocationEnable();

    /**
     * Used to enable/disable the Google Map Traffic Layer feature
     * @return Boolean
     */
    @DefaultBoolean(value = true)
    boolean isMapTrafficEnable();

    /**
     * Used to enable/disable the Google Map Compass feature
     * @return Boolean
     */
    @DefaultBoolean(value = false)
    boolean isMapCompasEnable();
}
