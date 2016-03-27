package com.tormentaLabs.riobus.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by limazix on 12/09/15.
 */
public class RioBusUtils {

    public static final String SERVER_ADDR = "http://dev.riob.us:8080/v3";
    public static final String PATTERN_FORMAT_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String PATTERN_FORMAT_DATE = "dd/MM/yyyy";
    public static final String PATTERN_FORMAT_TIME = "HH:mm:ss";

    public static final String URI_WILDCARD_APP_ID = "%id%";
    public static final String URI_PLAY_STORE_APP = "market://details?id=" + URI_WILDCARD_APP_ID;
    public static final String URI_PLAY_STORE_PAGE = "https://play.google.com/store/apps/details?id=" + URI_WILDCARD_APP_ID;

    public static Date parseStringToDateTime(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_FORMAT_TIMESTAMP);
        return dateFormat.parse(timestamp);
    }

    public static String parseDateToString(DateTime date, String format) {
        return date.toString(format);
    }
}
