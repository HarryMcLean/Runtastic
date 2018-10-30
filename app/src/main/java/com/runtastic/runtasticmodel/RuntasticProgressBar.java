package com.runtastic.runtasticmodel;
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

        Log.e("Testing", rControl.getUser(rControl.getLoggedInUser()).getEmail());

        //close realm link
        rControl.realmClose();

        //switch to the new view
        Intent intent = new Intent(RuntasticProgressBar.this, SideNavBar.class);
        startActivity(intent);

        //this clears memory?
        finish();
    }
}
