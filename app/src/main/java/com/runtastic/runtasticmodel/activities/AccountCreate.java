package com.runtastic.runtasticmodel.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.formedittextvalidator.DateValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.widget.FormEditText;
import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.helpers.PasswordsMatchValidator;
import com.runtastic.runtasticmodel.helpers.UserExistsValidator;
import com.runtastic.runtasticmodel.realm.RealmController;
import com.runtastic.runtasticmodel.realm.User;

import java.util.Calendar;

public class AccountCreate extends AppCompatActivity {

    private RealmController rControl;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


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
        final Button cancelButton = findViewById(R.id.button3);

        mDisplayDate = (TextView) findViewById(R.id.editText7);
        mDisplayDate.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //click listener started.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //grab the user's input from the view.
                FormEditText password = findViewById(R.id.editText4);
                FormEditText confirm = findViewById(R.id.editText6);
                FormEditText username = findViewById(R.id.editText3);

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
                            //Make sure user set the date
                            if(!mDisplayDate.getText().toString().equals("Tap Here")){
                                //create user and add to realm
                                User newUser = new User(rControl.nextUserId(), username.getText().toString(), mDisplayDate.getText().toString(), password.getText().toString());
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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreate.this, SignInPage.class);
                startActivity(intent);
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AccountCreate.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month += 1;
                day += 1;
                mDisplayDate.setText(day + "/" + month + "/" + year);
            }
        };
    }
}
