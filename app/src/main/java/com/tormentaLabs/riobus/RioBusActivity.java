package com.tormentaLabs.riobus;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.tormentaLabs.riobus.favorite.FavoriteActivity_;
import com.tormentaLabs.riobus.history.HistoryActivity;
import com.tormentaLabs.riobus.history.HistoryActivity_;
import com.tormentaLabs.riobus.map.MapFragment_;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_rio_bus)
public class RioBusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = RioBusActivity_.class.getName();

    private ActionBarDrawerToggle rioBusDrawerToggle;

    @ViewById(R.id.riobusToolbar)
    Toolbar rioBusToolBar;

    @ViewById(R.id.rio_bus_drawer_layout)
    DrawerLayout rioBusDrawerLayout;

    @ViewById(R.id.navigation)
    NavigationView navigationView;

    @AfterViews
    public void afterViews() {
        setupToolBar();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new MapFragment_())
                .commit();
    }

    private void setupToolBar() {
        rioBusDrawerToggle = new ActionBarDrawerToggle(this, rioBusDrawerLayout, rioBusToolBar, R.string.drawer_open, R.string.drawer_close);
        rioBusDrawerLayout.setDrawerListener(rioBusDrawerToggle);

        setSupportActionBar(rioBusToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        rioBusDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        rioBusDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (rioBusDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        rioBusDrawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.action_favorite:
                openActivity(new FavoriteActivity_());
                break;
            case R.id.action_history:
                openActivity(new HistoryActivity_());
                break;
            case R.id.action_rate_this_app:
                openPlayStore();
                break;
            case R.id.action_about:
                openInfo();
            default:
                break;
        }
        return true;
    }

    private void openActivity(AppCompatActivity activity) {
        Intent i = new Intent(this, activity.getClass());
        startActivity(i);
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();

        try {
            String uri = RioBusUtils.URI_PLAY_STORE_APP
                    .replace(RioBusUtils.URI_WILDCARD_APP_ID, appPackageName);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.getMessage());
            String uri = RioBusUtils.URI_PLAY_STORE_PAGE
                    .replace(RioBusUtils.URI_WILDCARD_APP_ID, appPackageName);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(i);
        }
    }

    private void openInfo() {
        Dialog dialog = new Dialog(RioBusActivity.this);
        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(getString(R.string.about_title));
        TextView tv = (TextView) dialog.findViewById(R.id.content);
        tv.setText(Html.fromHtml(getString(R.string.about_text)));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }
}