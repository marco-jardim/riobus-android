package com.tormentaLabs.riobus.history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ExpandableListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.history.adapter.HistoryListAdapter;
import com.tormentaLabs.riobus.history.controller.HistoryController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Used to expose history data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 * @// TODO: 19/11/15 ADD Search and endless scroll list
 */
@OptionsMenu(R.menu.activity_history)
@EActivity(R.layout.activity_history)
public class HistoryActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    private static final String TAG = HistoryActivity.class.getName();

    @ViewById(R.id.riobusHistoryToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.historyListView)
    ExpandableListView historyListView;

    @Bean
    HistoryListAdapter historyListAdapter;

    @Bean
    HistoryController historyController;

    @AfterViews
    void afterViews() {
        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        historyListView.setAdapter(historyListAdapter);
    }

    @OptionsItem(R.id.history_clear)
    void clearHistory(){
        showClearDialog();
    }

    public void showClearDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.history_clear_dialog);
        builder.setPositiveButton(R.string.ok, this);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int id) {
        historyController.clearHistory();
        historyListAdapter.populateLists();
    }
}
