package com.tormentaLabs.riobus.history.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Used to expose history data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 */
@EViewGroup(R.layout.view_history_group_header)
public class HistoryGroupHeaderView extends RelativeLayout {

    @ViewById(R.id.historyGroupTitle)
    TextView groupTitle;

    public HistoryGroupHeaderView(Context context) {
        super(context);
    }

    public void bind(String title) {
        groupTitle.setText(title);
    }

}
