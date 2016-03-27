package com.tormentaLabs.riobus.favorite.controller;

import android.provider.BaseColumns;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.core.utils.CoreUtils;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;
import com.tormentaLabs.riobus.favorite.utils.FavoriteUtils;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author limazix
 * @since 3.0
 */
@EBean
public class FavoriteController {

    private static final String TAG = FavoriteController.class.getName();

    /**
     * Method used to add a line as favorite
     * @param line
     */
    public void addToFavorites(LineModel line) {

        if(isFavorite(line)) return;

        FavoriteModel favorite = new FavoriteModel();
        favorite.line = line;
        favorite.createdAt = new DateTime().toString();
        favorite.save();
    }

    /**
     * Method used to check if a line is favorite
     * @param line
     * @return
     */
    public boolean isFavorite(LineModel line) {

        FavoriteModel favorite = getFavorite(line);

        return favorite != null ? true : false;
    }

    /**
     * Method used to get one particular line from favorite DB
     * @param line
     * @return It returns null if no entry was found
     */
    public FavoriteModel getFavorite(LineModel line) {
        return new Select().from(FavoriteModel.class)
                    .where("LINE = ? ", line.getId())
                    .executeSingle();
    }

    /**
     * Method used to get one particular line from favorite DB
     * @param keyword
     * @return It returns null if no entry was found
     */
    public List<FavoriteModel> searchFavorites(String keyword) {
        return new Select().from(FavoriteModel.class)
                .innerJoin(LineModel.class)
                .on(CoreUtils.TABLE_LINES_NAME + "." + BaseColumns._ID + "=" + FavoriteUtils.TABLE_FAVORITE_NAME + "." + BaseColumns._ID)
                .where(CoreUtils.TABLE_LINES_NAME + "." + CoreUtils.TABLE_LINES_COL_NUMBER + " LIKE '%" + keyword + "%'")
                .or(CoreUtils.TABLE_LINES_NAME + "." + CoreUtils.TABLE_LINES_COL_DESCRIPTION + " LIKE '%" + keyword + "%'")
                .limit(2) // TODO set global static variable
                .execute();
    }

    /**
     * Method used to get all favorites entries from the DB
     * @return
     */
    public List<FavoriteModel> getAllFavorites() {
        return new Select().from(FavoriteModel.class)
                .execute();
    }

    /**
     * Method used to remove a line from favorite list
     * @param line
     */
    public void removeFromFavorites(LineModel line) {
        FavoriteModel favorite = getFavorite(line);
        new Delete().from(FavoriteModel.class)
                .where("LINE = ? ", line.getId())
                .execute();
    }

}
