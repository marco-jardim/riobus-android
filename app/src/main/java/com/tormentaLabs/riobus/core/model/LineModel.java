package com.tormentaLabs.riobus.core.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.tormentaLabs.riobus.core.utils.CoreUtils;
import com.tormentaLabs.riobus.history.model.HistoryModel;

import java.util.List;

/**
 * @author limazix
 * @since 3.0.0
 * Created on 24/10/15
 */
@Table(name = CoreUtils.TABLE_LINES_NAME, id = BaseColumns._ID)
public class LineModel extends Model {

    @Column(name = CoreUtils.TABLE_LINES_COL_NUMBER)
    public String number;

    @Column(name = CoreUtils.TABLE_LINES_COL_DESCRIPTION)
    public String description;

    @Column(name = CoreUtils.TABLE_LINES_COL_NUMBER_OF_ACCESS)
    public int numberOfAccess;

    public LineModel() {
        super();
    }

    public LineModel(String number) {
        super();
        this.number = number;
    }

    /**
     * Exposing getMany method of one-to-many relationship
     * @param T Class/Table extending Model to be retrived
     * @param tableName Line Column name from the Class/Table to be retrived
     * @return A list of Class/Table elements
     */
    public List<? extends Model> getEntries(Class<? extends Model> T, String tableName) {
        return getMany(T, tableName);
    }
}
