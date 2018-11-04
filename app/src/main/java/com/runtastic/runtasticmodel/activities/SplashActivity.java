package com.runtastic.runtasticmodel.activities;
/********************************************
 * SplashActivity.java
 * S3427251 - Aaron Nettelbeck 10/18
 * For runtastic project
 * Based on splash screen example code
 * To be used for initialising database on first run
 * and any database maintenance that needs to be done on startup
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.runtastic.runtasticmodel.realm.RealmController;
import com.runtastic.runtasticmodel.realm.RunTracker;
import com.runtastic.runtasticmodel.realm.User;

public class SplashActivity extends AppCompatActivity {

    //private Realm realm;
    private RealmController rControl;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rControl = new RealmController();

        //clearing any logged in users
        rControl.clearLoggedInUsers();

//todo: Build new database on first run functions
        User user = new User(12346, "email2@email.com", "1999.1.1", "Password");
        rControl.addUser(user);

        //close realm connection and go to next view
        rControl.realmClose();
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
        finish();
    }
}
