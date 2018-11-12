package com.runtastic.runtasticmodel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.runtastic.runtasticmodel.R;

import java.util.ArrayList;
import java.util.Random;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatisticsFragment extends Fragment {

    View myView;
    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> barEntries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.statistics_layout, container, false);

        ViewPager viewPager = myView.findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);

        barChart = (BarChart) myView.findViewById(R.id.bargraph);

        createRandomBarGraph("2016/05/05", "2016/06/01");

//        ViewPager viewPager = myView.findViewById(R.id.view_pager);
//        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
//
//        TabLayout tabLayout = myView.findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);

        return myView;
    }

    public void createRandomBarGraph(String Date1, String Date2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1, mDate2);

            barEntries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random();
            for (int j = 0; j < dates.size(); j++) {
                max = 100f;
                value = random.nextFloat()*max;
                barEntries.add(new BarEntry(value, j));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        Description desc = new Description();
        desc.setText("Your Splits");
        barChart.setDescription(desc);
    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<>();
        while(startDate.compareTo(endDate) <= 0) {
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    public String getDate(Calendar cld) {
        String currDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.DAY_OF_MONTH);

        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(currDate);
            currDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currDate;
    }
}