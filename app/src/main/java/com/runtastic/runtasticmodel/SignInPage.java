package com.runtastic.runtasticmodel;

/********************************************
 * SignInPage.java
 * S3427251 - Aaron Nettelbeck 10/18
 * Covers the login page MVF of runtastic project
 * Combined with RealmController.java which provides the connections to the realm database.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//For commented out section of gps coordinate testing.
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//import android.location.Location;
//import com.google.android.gms.location.FusedLocationProviderClient;

public class SignInPage extends AppCompatActivity {

    //Link to the realm
    private RealmController rControl;

    //Used for gps testing, commenting out for now.
    //private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //linking to layout xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinpage);

        //open the realm and controller.
        rControl = new RealmController();

        //This log checks if the user added to the realm in the previous view has persisted to this view
        //even though the realm was closed only for testing - to remove later.
        try {
            Log.e("Test", rControl.getUser(12347).getEmail());
        }
        catch(Exception e){
            Log.e("Realm Exception", "User doesn't exist");
        }

        //Beginning of code to handle log in details.
        //Link to the button on the view
        final Button button = findViewById(R.id.button);

        //click listener started.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //user has tapped, check the user name was entered
                //todo: regex expression for emails

                //grab the user's input from the view.
                EditText username = findViewById(R.id.editText);
                EditText password = findViewById(R.id.editText2);

                if(!username.getText().toString().isEmpty())
                {
                    //user entered something
                    //check realm for username
                    if(rControl.checkUser(username.getText().toString())){
                        //user is there so safe to grab it.
                        User loginUser = rControl.getUser(username.getText().toString());

                        //now we have a user check the password
                        if(password.getText().toString().equals(loginUser.getPassword())){
                            //password matched, set user to be logged in
                            rControl.loginUser(loginUser);

                            //this was the last time we need the realm in this view.
                            rControl.realmClose();

                            //switch to the new view
                            Intent intent = new Intent(SignInPage.this, RuntasticProgressBar.class);
                            startActivity(intent);

                            //this clears memory?
                            finish();
                        }
                        else
                        {
                            //todo: Password wrong message code here
                            Log.e("REX", "Wrong Password");
                        }
                    }
                    else
                    {
                        //todo: User doesnt exist message code here
                        Log.e("REX", "User doesnt exist.");
                    }

                }

            }
        });

        //Code for onclick listening to tapping on the create account prompt.
        final TextView createAccText = findViewById(R.id.textView4);

        //click listener started.
        createAccText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this was the last time we need the realm in this view.
                rControl.realmClose();

                //switch to the new view
                Intent intent = new Intent(SignInPage.this, AccountCreate.class);
                startActivity(intent);

                //this clears memory?
                finish();
            }
        });


//This code can pull a weather from location, to read into api further to get from gps location.
//        WeatherMap weather = new WeatherMap();
//        weather.getJSON("Adelaide");

// Commenting out section but it is a working example of getting gps location
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            Log.e("Latitude: ", Double.toString(location.getLatitude()));
//                            Log.e("Longitude", Double.toString(location.getLongitude()));
//                        }
//                    }
//                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}