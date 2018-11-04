package com.runtastic.runtasticmodel.activities;
/********************************************
 * RuntasticProgressBar.java
 * S3427251 - Aaron Nettelbeck 10/18
 * For runtastic project
 * Loading screen to be used for initialising any remote connections
 * or data that might need to be grabbed before transferring control
 * to user.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.runtastic.runtasticmodel.helpers.ProgressBarAnimation;
import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.LatLong;
import com.runtastic.runtasticmodel.realm.RealmController;
import com.runtastic.runtasticmodel.realm.RunTracker;

import io.realm.RealmResults;


public class RuntasticProgressBar extends AppCompatActivity {

    //create realm link
    private RealmController rControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //grab view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        //animation code for smooth loading
        final ProgressBar progBar = findViewById(R.id.progressBar);
        ProgressBarAnimation anim = new ProgressBarAnimation(progBar, 0f, 100f);
        anim.setDuration(3000);
        progBar.startAnimation(anim);

        //open realm link
        rControl = new RealmController();

        Log.e("Testing", rControl.getLoggedInUser().getEmail());

        //Example RunTracker use
        //Either build the runTrack slowly, then save it.
        RunTracker rt1 = new RunTracker();
        rt1.setRid(rControl.nextRuntrackId());
        rt1.setAverageSpeed(25);
        rt1.setDistance(11);
        rt1.setDat("123456");
        rt1.setEstimatedCalories(800);
        rt1.setTimeTaken(30);
        LatLong coord1 = new LatLong(25.0, 25.0);
        rt1.addCoord(coord1);
        rControl.saveRunTrack(rt1);

        //Or have all the variables and make it in one go
        RunTracker rt2 = new RunTracker(rControl.nextRuntrackId(), 25.0, 11.0, 744.0, 30.0, 30.0, "123457");
        LatLong coord2 = new LatLong(30.0, 30.0);
        rt2.addCoord(coord2);
        rControl.saveRunTrack(rt2);

        //Once saved you can grab the following stuff
        //Fastest, longest etc
        RunTracker rt3 = rControl.getFastestAverage();
        String string = rt3.getAverageSpeed() + " " + rt3.getRid();
        Log.e("Testing Fastest Average", string);

        rt3 = rControl.getFastest();
        string = rt3.getMaxSpeed() + " " + rt3.getRid();
        Log.e("Testing Max Speed", string);

        rt3 = rControl.getLongest();
        string = rt3.getDistance() + " " + rt3.getRid();
        Log.e("Testing Longest Run", string);

        Log.e("Testing Last Run", Double.toString(rControl.getLastRunTrack().getMaxSpeed()));
        //close realm link
        rControl.realmClose();

        //switch to the new view
        Intent intent = new Intent(RuntasticProgressBar.this, SideNavBar.class);
        startActivity(intent);

        //this clears memory?
        finish();
    }
}
