package com.tormentaLabs.riobus.core.utils;

import android.provider.BaseColumns;

/**
 * Created by limazix on 12/01/16.
 */
public class CoreUtils {
    public static final String TABLE_LINES_NAME = "LINES";
    public static final String TABLE_LINES_COL_NUMBER = "NUMBER";
    public static final String TABLE_LINES_COL_NUMBER_OF_ACCESS = "NUMBER_OF_ACCESS";
    public static final String TABLE_LINES_COL_DESCRIPTION = "DESCRIPTION";
    public static final String TABLE_LINES_WHERE_NUMBER = TABLE_LINES_NAME + "." + TABLE_LINES_COL_NUMBER + " = ? ";

    /**
     * Method used to create the 'on' statement when join two tables by theirs ids
     * @param firstTable Name of the first table to be joined
     * @param secondTable Name of the second table to be joined
     * @return The 'on' by theirs ids
     */
    public static String joinByIdOnStatement(String firstTable, String secondTable) {
        return firstTable + "." + BaseColumns._ID + "=" + secondTable + "." + BaseColumns._ID;
    }
}
