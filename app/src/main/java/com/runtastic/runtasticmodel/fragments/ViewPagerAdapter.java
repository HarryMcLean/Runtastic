package com.runtastic.runtasticmodel.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

//    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
//        childFragments = new Fragment[] {
//                new StatisticsFragment(),
//                new ActivityStatisticsConditions(),
//                new ActivityStatisticsRoute()
//        };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StatisticsFragment();
            case 1:
                return new ActivityStatisticsConditions();
            case 2:
                return new ActivityStatisticsRoute();
        }
        return null;
//        return childFragments[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = getItem(position).getClass().getName();
//        return title.subSequence(title.lastIndexOf(".") + 1, title.length());
//    }

}
