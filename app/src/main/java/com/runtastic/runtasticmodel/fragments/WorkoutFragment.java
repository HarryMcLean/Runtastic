package com.runtastic.runtasticmodel.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.LatLong;

public class WorkoutFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private static final String TAG = "MapFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private BroadcastReceiver broadcastReceiver;
    private MapView mapView;

    View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.workout_layout,container,false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        //getLocationPermission();
        mapView = (MapView) myView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return myView;
    }

    private void initMap(){
        SupportMapFragment mapFragment =
                (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(myView.getContext().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(myView.getContext().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    permissions, LOCATION_PERMISSION_REQUEST_CODE );
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted=true;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                   for(int i=0; i <grantResults.length; i++){
                      if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                          mLocationPermissionsGranted = false;
                          return;
                      }
                   }
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    private void getDeviceLocation(){
        if(broadcastReceiver == null){
            Log.e("Test", "New receiver");
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent){
                    try{
                        //weather.getWeather((String)intent.getExtras().get("coord"));

                        LatLong latlong = new LatLong(intent.getExtras().get("coord").toString());
                        Log.e("GPS", intent.getExtras().get("coord").toString());
                        moveCamera(new LatLng(latlong.getLatitude(),
                                        latlong.getLongitude()),
                                DEFAULT_ZOOM);
                    }
                    catch(Exception e) {
                    }
                }
            };
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("locationUpdate"));
        Log.e("Test", "GPS Updates start");
        /*mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(myView.getContext());
        try{
            if(mLocationPermissionsGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                                  currentLocation.getLongitude()),
                                     DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG, "Current location is null");
                            Toast.makeText(myView.getContext(), "unable to get location",
                                            Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG, e.getMessage());
        }*/
    }

    private void moveCamera(LatLng latlng, float zoom){
        Log.d(TAG, "Moving camera to lat: " +
                latlng.latitude + ", lng: " + latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //if(mLocationPermissionsGranted){
            getDeviceLocation();

        //}
    }

}
