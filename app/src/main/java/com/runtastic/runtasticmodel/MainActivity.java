package com.runtastic.runtasticmodel;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public Realm realm;
    private RealmController rControl;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        rControl = new RealmController(realm);

        User user = new User(12345, "email@email.com", "1999.1.1", "Password");
        rControl.addUser(user);
        Log.e("Test", rControl.getUser(12345).getEmail());

        WeatherMap weather = new WeatherMap();
        weather.getJSON("Adelaide");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e("Latitude: ", Double.toString(location.getLatitude()));
                            Log.e("Longitude", Double.toString(location.getLongitude()));
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }




}