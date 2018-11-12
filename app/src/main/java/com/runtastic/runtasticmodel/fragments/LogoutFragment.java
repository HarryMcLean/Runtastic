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
import com.runtastic.runtasticmodel.realm.LatLong;
import com.runtastic.runtasticmodel.realm.RealmController;

import java.util.List;

public class LogoutFragment extends Fragment {

    View myView;
    private RealmController rControl;

    private WeatherMap weather = new WeatherMap();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "New Object");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.logout_layout,container,false);
        Log.e("Test", "View restored");
        return myView;
    }

    public static LogoutFragment createInstance(){
        LogoutFragment fragment = new LogoutFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

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

                //remove all fragments
                List<Fragment> al = getActivity().getSupportFragmentManager().getFragments();
                for (Fragment frag : al)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(frag).commit();
                }

                //switch to sign in view
                Intent intent = new Intent(getActivity(), SignInPage.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
