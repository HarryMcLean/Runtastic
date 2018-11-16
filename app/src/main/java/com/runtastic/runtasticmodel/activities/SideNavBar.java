package com.runtastic.runtasticmodel.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.fragments.CalendarFragment;
import com.runtastic.runtasticmodel.fragments.DiaryFragment;
import com.runtastic.runtasticmodel.fragments.LogoutFragment;
import com.runtastic.runtasticmodel.fragments.StartFragment;
import com.runtastic.runtasticmodel.fragments.StatisticsFragment;
import com.runtastic.runtasticmodel.fragments.StopwatchFragment;
import com.runtastic.runtasticmodel.fragments.WorkoutFragment;


public class SideNavBar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private int ref = 0;

    private final String LOGOUT_TAG = "logoutFragment";
    private final String WORKOUT_TAG = "workoutFragment";
    private final String STATISTICS_TAG = "statisticsFragment";
    private final String DIARY_TAG = "diaryFragment";
    private final String CALENDAR_TAG = "calendarFragment";
    private final String STOPWATCH_TAG = "stopwatchFragment";
    private final String START_TAG = "startFragment";

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

        displaySelectedScreen(new StartFragment(), START_TAG);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //Intent openGPS = new Intent(this,GpsTracker.class);
        Fragment fragment = null;

        switch(menuItem.getItemId()){
            case R.id.nav_record:
                //startActivity(openGPS);
                fragment = getSupportFragmentManager().findFragmentByTag(WORKOUT_TAG);
                if(fragment != null){
                    displaySelectedScreen(fragment, WORKOUT_TAG);
                }
                else
                {
                    displaySelectedScreen(new WorkoutFragment(), WORKOUT_TAG);
                }
                break;
            case R.id.nav_statistics:
                fragment = getSupportFragmentManager().findFragmentByTag(STATISTICS_TAG);
                if(fragment != null){
                    displaySelectedScreen(fragment, STATISTICS_TAG);
                }
                else
                {
                    displaySelectedScreen(new StatisticsFragment(), STATISTICS_TAG);
                }
                break;
            case R.id.nav_diary:
                fragment = getSupportFragmentManager().findFragmentByTag(DIARY_TAG);
                //if(fragment != null){
                //    displaySelectedScreen(fragment, DIARY_TAG);
                //}
                //else
                //{
                    displaySelectedScreen(new DiaryFragment(), DIARY_TAG);
                //}
                break;
            case R.id.nav_calendar:
                fragment = getSupportFragmentManager().findFragmentByTag(CALENDAR_TAG);
                //if(fragment != null){
                //    displaySelectedScreen(fragment, CALENDAR_TAG);
                //}
                //else
                //{
                    displaySelectedScreen(new CalendarFragment(), CALENDAR_TAG);
                //}
                break;
            case R.id.nav_stopwatch:
                fragment = getSupportFragmentManager().findFragmentByTag(STOPWATCH_TAG);
                if(fragment != null){
                    displaySelectedScreen(fragment, STOPWATCH_TAG);
                }
                else
                {
                    displaySelectedScreen(new StopwatchFragment(), STOPWATCH_TAG);
                }
                break;
            case R.id.nav_logout:
                fragment = getSupportFragmentManager().findFragmentByTag(LOGOUT_TAG);
                if(fragment != null){
                    displaySelectedScreen(fragment, LOGOUT_TAG);
                }
                else
                {
                    displaySelectedScreen(LogoutFragment.createInstance(), LOGOUT_TAG);
                }
                break;
        }

        //if (fragment != null){
        //    displaySelectedScreen(fragment);
        //}

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().
        replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null).commit();

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
