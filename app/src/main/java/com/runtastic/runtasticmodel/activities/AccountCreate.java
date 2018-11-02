package com.runtastic.runtasticmodel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.RealmController;
import com.runtastic.runtasticmodel.realm.User;

public class AccountCreate extends AppCompatActivity {

    private RealmController rControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //grab view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountcreate);

        //open the realm and controller.
        rControl = new RealmController();

        //Beginning of code to handle log in details.
        //Link to the button on the view
        final Button button = findViewById(R.id.button2);

        //click listener started.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //grab the user's input from the view.
                EditText password = findViewById(R.id.editText4);
                EditText confirm = findViewById(R.id.editText6);
                EditText username = findViewById(R.id.editText3);
                EditText birthday = findViewById(R.id.editText7);

                //check password match
                if(password.getText().toString().equals(confirm.getText().toString())){
                    Log.e("REX", "Password match?");
                    //password matches
                    //check if user already exists
                    if(!rControl.checkUser(username.getText().toString())){
                        //todo: code for checking birthday value
                        //user doesn't exist
                        //add user
                        User newUser = new User(rControl.nextUserId(), username.getText().toString(), birthday.getText().toString(), password.getText().toString());
                        rControl.addUser(newUser);
                        Log.e("REX", "User added?");

                        //this was the last time we need the realm in this view.
                        rControl.realmClose();

                        //switch to the new view
                        Intent intent = new Intent(AccountCreate.this, SignInPage.class);
                        startActivity(intent);

                        //this clears memory?
                        finish();
                    }
                    else
                    {
                        //todo: code for prompt about user already exists
                    }
                }
                else
                {
                    //todo: code for prompt about password doesn't match
                }
            }
        });
    }
}
