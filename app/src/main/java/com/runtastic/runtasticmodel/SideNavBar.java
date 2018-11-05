package com.runtastic.runtasticmodel;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class SideNavBar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_nav_bar);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        displaySelectedScreen(new StartFragment());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //Intent openGPS = new Intent(this,GpsTracker.class);
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_record:
                //if(isServicesOK()==true) {
                    fragment = new WorkoutFragment();
                //}
                break;
            case R.id.nav_statistics:
                fragment = new StatisticsFragment();
                break;
            case R.id.nav_diary:
                fragment = new DiaryFragment();
                break;
            case R.id.nav_calendar:
                fragment = new CalendarFragment();
                break;
            case R.id.nav_stopwatch:
                fragment = new StopwatchFragment();
                break;
            case R.id.nav_logout:
                fragment = new LogoutFragment();
                break;
        }

        if (fragment != null) {
            displaySelectedScreen(fragment);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }



    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SideNavBar.this);
    if(available == ConnectionResult.SUCCESS){
        // user can make map requests
        Log.d(TAG, "Google play services is working");
        return true;
    }
    else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
        // an error occurred but it is resolvable
        Log.d(TAG, "A resolvable error occurred");
        Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SideNavBar.this, available, ERROR_DIALOG_REQUEST);
        dialog.show();
    } else{
        Toast.makeText(this, "We can't make map requests", Toast.LENGTH_SHORT).show();
    }
    return false;
    }


}
