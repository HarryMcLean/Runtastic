package com.runtastic.runtasticmodel;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.Locale;

public class Chronometer implements Runnable {

    //some constants for milliseconds to hours,minutes,and seconds conversion
    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLS_TO_HOURS = 3600000;

    Fragment stopWatchFragment;
    long mStartTime;

    boolean mIsRunning;

    public Chronometer(Fragment fragment) {
        stopWatchFragment = fragment;
    }

    public Chronometer(Fragment fragment, long startTime) {
        this(fragment);
        mStartTime = startTime;
    }


    public void start() {
        if (mStartTime == 0) {
            mStartTime = System.currentTimeMillis();
        }

        mIsRunning = true;
    }

    public void stop() {
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public long getStartTime() {
        return mStartTime;
    }

    @Override
    public void run() {


        while (mIsRunning) {

            //here we calculate the different of starting time and current time
            long since = System.currentTimeMillis() - mStartTime;


            //converts the resulted time difference into hours, minutes, seconds, milliseconds
            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLS_TO_HOURS)) % 24);
            int millis = (int) since % 1000;//the last 3 digits of milliseconds


            ((StopwatchFragment) stopWatchFragment).updateTimerText(String.format("%02d:%02d:%02d:%03d"
                    , hours, minutes, seconds, millis));

        }
    }

}
