package com.runtastic.runtasticmodel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.DateValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.widget.FormEditText;
import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.helpers.PasswordsMatchValidator;
import com.runtastic.runtasticmodel.helpers.UserExistsValidator;
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
                FormEditText password = findViewById(R.id.editText4);
                FormEditText confirm = findViewById(R.id.editText6);
                FormEditText username = findViewById(R.id.editText3);
                FormEditText birthday = findViewById(R.id.editText7);

                //check its an email
                username.addValidator(new EmailValidator("Not a valid email.") );
                if(username.testValidity()) {
                    //check doesn't exist
                    username.addValidator(new UserExistsValidator(rControl));
                    Log.e("ACC", "Valid email.");
                    if(username.testValidity()){
                        Log.e("ACC", "New username");
                        //check passwords match
                        password.addValidator(new PasswordsMatchValidator(confirm.getText().toString()));
                        confirm.addValidator(new PasswordsMatchValidator(password.getText().toString()));
                        if(password.testValidity() && confirm.testValidity()){
                            Log.e("ACC", "passwords match");
                            //check the date is a valid date
                            birthday.addValidator(new DateValidator("Not a date.", "DefaultDate"));
                            if(birthday.testValidity()) {
                                Log.e("ACC", "Date format worked");
                                //create user and add to realm
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
                        }
                    }
                }

            }
        });
    }
}
