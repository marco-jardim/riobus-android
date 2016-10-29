package com.tormentaLabs.riobus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.fragments.AvailableLinesFragment;
import com.tormentaLabs.riobus.fragments.OnLineInteractionListener;
import com.tormentaLabs.riobus.fragments.RecentSearchesFragment;
import com.tormentaLabs.riobus.models.Line;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnLineInteractionListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.recents_fragment) FrameLayout recentsList;
    @BindView(R.id.available_fragment) FrameLayout availableLinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment recentsFragment = fragmentManager.findFragmentById(R.id.recents_fragment);
        if (recentsFragment == null) {
            recentsFragment = new RecentSearchesFragment();
            fragmentManager.beginTransaction().replace(R.id.recents_fragment, recentsFragment).commit();
        }

        Fragment availableLinesFragment = fragmentManager.findFragmentById(R.id.available_fragment);
        if (availableLinesFragment == null) {
            availableLinesFragment = new AvailableLinesFragment();
            fragmentManager.beginTransaction().replace(R.id.available_fragment, availableLinesFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.nav_search: break;
            case R.id.nav_favorites: break;
            case R.id.nav_history: break;
            case R.id.nav_send_feedback: break;
            case R.id.nav_about: break;
            case R.id.nav_rate_app: break;
            case R.id.nav_like_facebook: break;
            default: break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLineInteraction(Line line) {
        if (line != null) {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra(MapActivity.LINE_TITLE, line.getLine());
            startActivity(intent);
        }
    }
}
