package com.runtastic.runtasticmodel;

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
import android.view.MenuItem;


public class SideNavBar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_nav_bar);

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

        switch(menuItem.getItemId()){
            case R.id.nav_record:
                //startActivity(openGPS);
                fragment = new WorkoutFragment();
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

        if (fragment != null){
            displaySelectedScreen(fragment);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}
