package com.tormentaLabs.riobus.favorite.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.utils.FavoriteUtils;

import java.sql.Date;

/**
 * @author limazix
 * @since 3.0
 * Created on 24/10/15
 */
@Table(name = FavoriteUtils.TABLE_FAVORITE_NAME, id = BaseColumns._ID)
public class FavoriteModel extends Model {

    @Column(name = FavoriteUtils.TABLE_FAVORITE_COL_CREATED_AT)
    public String createdAt;

    @Column(name = FavoriteUtils.TABLE_FAVORITE_COL_LINE)
    public LineModel line;

    public FavoriteModel() {
        super();
    }
}
