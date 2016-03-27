package com.tormentaLabs.riobus.core.controller;

import android.database.Cursor;
import android.util.Log;

import com.activeandroid.Cache;
import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.core.utils.CoreUtils;

import org.androidannotations.annotations.EBean;

/**
 * Used to control number data
 * @author limazix
 * @since 3.0.0
 * Created at 24/10/15
 */
@EBean
public class LineController {

    private static final String TAG = LineController.class.getName();

    public LineModel createIfNotExists(String line) {

        LineModel lineModel = new Select().from(LineModel.class)
                .where(CoreUtils.TABLE_LINES_WHERE_NUMBER, line)
                .executeSingle();

        if (lineModel == null) {
            lineModel = new LineModel();
            lineModel.number = line;
            lineModel.numberOfAccess = 1;
        } else {
            int counter = lineModel.numberOfAccess;
            lineModel.numberOfAccess = counter + 1;
        }

        lineModel.save();
        return lineModel;
    }

    public LineModel getLine(String line) {
        return new Select().from(LineModel.class)
                .where(CoreUtils.TABLE_LINES_WHERE_NUMBER, line)
                .executeSingle();
    }

    public Cursor fetchCursor() {
        String resultRecords = new Select()
                .from(LineModel.class)
                .orderBy(CoreUtils.TABLE_LINES_COL_NUMBER_OF_ACCESS + " DESC")
                .toSql();

        return Cache.openDatabase().rawQuery(resultRecords, null);
    }

    public Cursor fetchCursor(String searchTerm) {
        String resultRecords = new Select()
                .from(LineModel.class)
                .where(CoreUtils.TABLE_LINES_COL_NUMBER + " LIKE '%" + searchTerm + "%'")
                .or(CoreUtils.TABLE_LINES_COL_DESCRIPTION + " LIKE '%" + searchTerm + "%'")
                .orderBy(CoreUtils.TABLE_LINES_COL_NUMBER_OF_ACCESS + " DESC")
                .toSql();

        return Cache.openDatabase().rawQuery(resultRecords, null);
    }
}