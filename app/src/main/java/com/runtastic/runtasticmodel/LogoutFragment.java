package com.runtastic.runtasticmodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LogoutFragment extends Fragment {

    View myView;
    private RealmController rControl;

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
                rControl.clearLoggedInUsers();
                rControl.realmClose();

                //switch to the new view
                Intent intent = new Intent(getActivity(), SignInPage.class);
                startActivity(intent);
            }
        });

        return myView;

    }
}
