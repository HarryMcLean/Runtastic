package com.runtastic.runtasticmodel.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.activities.SignInPage;
import com.runtastic.runtasticmodel.helpers.WeatherMap;
import com.runtastic.runtasticmodel.realm.RealmController;

public class LogoutFragment extends Fragment {

    View myView;
    private RealmController rControl;

    private BroadcastReceiver broadcastReceiver;
    private WeatherMap weather = new WeatherMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.logout_layout,container,false);

        //Beginning of code to handle log in details.
        //Link to the button on the view
        final Button button = myView.findViewById(R.id.button4);

        //click listener started.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //log user out
                rControl = new RealmController();

                //logout current user ensuring remembered is cleared too
                rControl.logOutUser(rControl.getLoggedInUser());

                //ensure all other users are logged out too in case
                rControl.clearLoggedInUsers();
                rControl.realmClose();

                //switch to the new view
                Intent intent = new Intent(getActivity(), SignInPage.class);
                startActivity(intent);
            }
        });

        return myView;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent){
                    try{
                        //weather.getWeather((String)intent.getExtras().get("coord"));
                        weather.getWeather(intent.getExtras().get("coord").toString());
                    }
                    catch(Exception e) {
                    }
                }
            };
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("locationUpdate"));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(broadcastReceiver != null){
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

}
