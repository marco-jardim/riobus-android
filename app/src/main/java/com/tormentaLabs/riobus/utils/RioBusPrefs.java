package com.tormentaLabs.riobus.utils;

import com.tormentaLabs.riobus.R;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Used to store/access global user preferences
 * @author limazix
 * @since 2.0
 * Created at 01/09/15
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface RioBusPrefs {

    /**
     * Used to store the server address url
     * @return String
     */
    @DefaultRes(R.string.server_addr_v3)
    String getServerAddr();

}
