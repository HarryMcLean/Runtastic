package com.runtastic.runtasticmodel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtastic.runtasticmodel.R;

public class StatisticsPagerFragment extends Fragment {

    View myView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.statistics_pager, container, false);
        ViewPager viewPager = myView.findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);

//        TabLayout tabLayout = myView.findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);


        return myView;
    }
}
