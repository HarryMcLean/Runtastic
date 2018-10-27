package com.runtastic.runtasticmodel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GpsTracker extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent openGPS = new Intent(this,GpsTracker.class);
        switch(menuItem.getItemId()){
            case R.id.nav_record:
                startActivity(openGPS);
                break;
            case R.id.nav_statistics:
                Toast.makeText(this, "(Open Activity Statistics)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_diary:
                Toast.makeText(this, "(Open Diary)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_calendar:
                Toast.makeText(this, "(Open Calendar)", Toast.LENGTH_SHORT).show();;
                break;
            case R.id.nav_stopwatch:
                Toast.makeText(this, "(Open Stopwatch)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "(Logout)", Toast.LENGTH_SHORT).show();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
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
