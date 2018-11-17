package com.runtastic.runtasticmodel.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.helpers.WeatherMap;
import com.runtastic.runtasticmodel.realm.RealmController;

public class ActivityStatisticsConditions extends Fragment {

    private View view;
    private RealmController rControl = new RealmController();
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_statistics_conditions, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        rControl.realmOpen();

        final TextView MIN_TEMP = view.findViewById(R.id.minTempView);
        final TextView MAX_TEMP = view.findViewById(R.id.maxTempView);
        final TextView CITY = view.findViewById(R.id.cityView);
        final TextView CURR_TEMP = view.findViewById(R.id.tempView);

        rControl.realmClose();

        if (broadcastReceiver == null) {
            Log.e("Test", "New receiver");
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        WeatherMap weather = new WeatherMap();
                        weather.getWeather(intent.getExtras().get("coord").toString(), CURR_TEMP, MIN_TEMP, MAX_TEMP, CITY);
                    } catch (Exception e) {

                    }
                }
            };
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("locationUpdate"));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(broadcastReceiver != null){
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }
}
