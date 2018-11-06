package com.runtastic.runtasticmodel.activities;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import android.location.LocationListener;
import android.provider.Settings;
import android.util.Log;

import javax.annotation.Nullable;

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(){

        Log.e("GPService", "GPS Service started");
        listener = new LocationListener(){
            @Override
            public void onLocationChanged(Location location){
                Intent i = new Intent("locationUpdate");
                i.putExtra("coord", location.getLatitude() + "|" + location.getLongitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle){

            }

            @Override
            public void onProviderEnabled(String s){

            }

            //If provider is turned off send user to phone settings.
            @Override
            public void onProviderDisabled(String s){
               Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(listener);
        }
    }
}
