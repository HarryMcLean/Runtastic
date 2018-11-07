package com.runtastic.runtasticmodel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.helpers.Chronometer;

public class StopwatchFragment extends Fragment {


    private TextView mTvTime;
    private Button mBtnStart;
    private Button mBtnLap;
    private Button mBtnStop;

    private Chronometer mChronometer;
    private Thread mThreadChrono;

    public boolean haltDisplayForLap= false;
    private Fragment stopWatchFragment;

    View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.stopwatch_layout,container,false);



        stopWatchFragment = this;

        mTvTime = myView.findViewById(R.id.tv_time);
        mBtnStart = myView.findViewById(R.id.btn_start);
        mBtnLap = myView.findViewById(R.id.btn_lap);
        mBtnStop = myView.findViewById(R.id.btn_stop);

        //btn_start click handler
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if chronometer has not been instantiated before...
                if(mChronometer == null){
                    //instantiated the chronometer
                    mChronometer = new Chronometer(stopWatchFragment);
                    //run the chronometer on a separate thread
                    mThreadChrono = new Thread(mChronometer);
                    mThreadChrono.start();

                    //start the chronometer
                   mChronometer.start();
                }
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer had been instantiated before
                if(mChronometer != null){
                    //stop the chronometer
                    mChronometer.stop();
                    //stop the thread
                    mThreadChrono.interrupt();
                    mThreadChrono = null;
                    //kill the chrono class
                    mChronometer = null;
                }else{
                    String resetTimeText= "00:00:00:000";
                    //TODO: Integer.toString(R.string.timerStart);
                    // fix this reference - it is returning an int and the parse is helping
                    updateTimerText(resetTimeText);
                }

            }
        });

        mBtnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer had been instantiated before
                if(mChronometer != null){
                    //toggle lap display
                    haltDisplayForLap = !haltDisplayForLap;

                }
            }
        });
        return myView;
    }


    public void updateTimerText(final String timeAsText) {

        if(!haltDisplayForLap){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvTime.setText(timeAsText);
                }
            });
        }

    }
}





