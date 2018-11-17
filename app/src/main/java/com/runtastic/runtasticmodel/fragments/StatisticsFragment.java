package com.runtastic.runtasticmodel.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.helpers.WeatherMap;
import com.runtastic.runtasticmodel.realm.RealmController;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatisticsFragment extends Fragment {

    View myView = null;

    private RealmController rControl = new RealmController();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.statistics_layout, container, false);
        ViewPager viewPager = myView.findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);

//        TabLayout tabLayout = myView.findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);


        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();

        rControl.realmOpen();

        TextView distance = myView.findViewById(R.id.distance);
        TextView time = myView.findViewById(R.id.totalTime);
        TextView calories = myView.findViewById(R.id.calories);
        TextView speed = myView.findViewById(R.id.speed);
        TextView fastestSpeed = myView.findViewById(R.id.fastestSpeed);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        distance.setText(df.format(rControl.getLastRunTrack().getDistance()) + "km");
        time.setText(df.format(rControl.getLastRunTrack().getTimeTaken()));
        calories.setText(df.format(rControl.getLastRunTrack().getEstimatedCalories()));
        speed.setText(df.format(rControl.getLastRunTrack().getAverageSpeed()));
        fastestSpeed.setText(df.format(rControl.getLastRunTrack().getMaxSpeed()));

        rControl.realmClose();
    }
}