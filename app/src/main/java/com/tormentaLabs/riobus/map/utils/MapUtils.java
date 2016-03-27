package com.tormentaLabs.riobus.map.utils;

import android.view.inputmethod.EditorInfo;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limazix on 01/09/15.
 */
@EBean
public class MapUtils {

    public static boolean isValidString(String entry) {
        return !(entry == null || entry.equals("") || entry.trim().equals(""));
    }

    public static boolean isSearchAction(int actionId) {
        return actionId == EditorInfo.IME_ACTION_SEARCH;
    }

    public static String[] separateMultiLines(String lines){
        return lines.split(",");
    }
}
