package com.tormentaLabs.riobus.history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.history.adapter.HistoryListAdapter;
import com.tormentaLabs.riobus.history.controller.HistoryController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Used to expose history data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 * @// TODO: 19/11/15 ADD Search and endless scroll list
 */
@EActivity(R.layout.activity_history)
public class HistoryActivity extends AppCompatActivity {

    @ViewById(R.id.riobusHistoryToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.historyListView)
    ExpandableListView historyListView;

    @Bean
    HistoryListAdapter historyListAdapter;

    @AfterViews
    void afterViews() {
        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        historyListView.setAdapter(historyListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.history_clear:
                clearHistory();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearHistory(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.history_clear_dialog);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new HistoryController().deleteHistory();
                historyListAdapter.populateList();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }
}
