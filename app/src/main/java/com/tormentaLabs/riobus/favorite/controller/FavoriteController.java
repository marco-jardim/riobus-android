package com.tormentaLabs.riobus.favorite.controller;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

/**
 * @author limazix
 * @since 3.0
 */
@EBean
public class FavoriteController {

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
     * Method used to get one particular line from favorite data base
     * @param line
     * @return It returns null if no entry was found
     */
    public FavoriteModel getFavorite(LineModel line) {
        return new Select().from(FavoriteModel.class)
                    .where("LINE = ? ", line)
                    .executeSingle();
    }

    /**
     * Method used to remove a line from favorite list
     * @param line
     */
    public void removeFromFavorites(LineModel line) {
        new Delete().from(FavoriteModel.class)
                .where("LINE = ? ", line)
                .execute();
    }

}
