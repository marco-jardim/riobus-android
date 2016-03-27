package com.tormentaLabs.riobus.history.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.history.utils.HistoryUtils;

import java.util.List;

/**
 * @author limazix
 * @since 2.1
 * Created on 05/10/15
 */
@Table(name = HistoryUtils.TABLE_HISTORY_NAME, id = BaseColumns._ID)
public class HistoryModel extends Model {

    @Column(name = HistoryUtils.TABLE_HISTORY_COL_LINE)
    public LineModel line;

    @Column(name = HistoryUtils.TABLE_HISTORY_COL_CREATED_AT)
    public String createdAt;

    public HistoryModel() {
        super();
    }

}
