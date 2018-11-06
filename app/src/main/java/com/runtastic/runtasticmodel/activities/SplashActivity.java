package com.runtastic.runtasticmodel.activities;
/********************************************
 * SplashActivity.java
 * S3427251 - Aaron Nettelbeck 10/18
 * For runtastic project
 * Based on splash screen example code
 * To be used for initialising database on first run
 * and any database maintenance that needs to be done on startup
 */

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.runtastic.runtasticmodel.realm.RealmController;
import com.runtastic.runtasticmodel.realm.User;

public class SplashActivity extends AppCompatActivity {

    //private Realm realm;
    private RealmController rControl;

    /* GPS code
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onResume(){
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent){
                    String lat = (String) intent.getExtras().get("latitude");
                    Log.e("GPS", lat);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("locationUpdate"));
    }
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rControl = new RealmController();

        runtimePermissions();

        //clearing any logged in users
        rControl.clearLoggedInUsers();

        startGPSService();

//todo: Build new database on first run functions
        User user = new User(12346, "email2@email.com", "1999.1.1", "Password");
        rControl.addUser(user);

        if(rControl.userWasRemembered())
        {
            rControl.realmClose();
            Intent intent = new Intent(this, RuntasticProgressBar.class);
            startActivity(intent);
        }
        else {
            rControl.realmClose();
            Intent intent = new Intent(this, SignInPage.class);
            startActivity(intent);
        }
        finish();
    }

    private boolean runtimePermissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1001);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1001){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                //start gps service
                startGPSService();
            }
            else
            {
                runtimePermissions();
            }
        }
    }

    public void startGPSService(){
        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //needed if you use the above section
        //if(broadcastReceiver != null){
        //    unregisterReceiver(broadcastReceiver);
        //}
    }

}
