package com.runtastic.runtasticmodel.fragments;

import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.LatLong;
import com.runtastic.runtasticmodel.realm.RealmController;

import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

public class ActivityStatisticsRoute extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;

    private View view;
    private RealmController rControl = new RealmController();
    private GoogleMap mMap;
    private static final String TAG = "MapFragment";
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private MapView mapView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.activity_statistics_route, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        //getLocationPermission();
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    private void initMap(){
        SupportMapFragment mapFragment =
                (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void moveCamera(LatLng latlng, float zoom){
        Log.d(TAG, "Moving camera to lat: " +
                latlng.latitude + ", lng: " + latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
    }

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < rControl.getLastRunTrack().getCoords().size(); i++) {
            if(i + 1 < rControl.getLastRunTrack().getCoords().size()) {
                LatLong latLong = rControl.getLastRunTrack().getCoords().get(i);
                LatLong nextLatLong = rControl.getLastRunTrack().getCoords().get(i + 1);
                Polyline polyline = mMap.addPolyline(new PolylineOptions().add(new LatLng(nextLatLong.getLatitude(),
                        nextLatLong.getLongitude()), new LatLng(latLong.getLatitude(),
                        latLong.getLongitude())).width(5).color(Color.RED));
                moveCamera(new LatLng(latLong.getLatitude(),
                                latLong.getLongitude()),
                        DEFAULT_ZOOM);
            }
        }

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(broadcastReceiver != null){
            Log.e("Test", "GPS updates stopped");
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }
}
