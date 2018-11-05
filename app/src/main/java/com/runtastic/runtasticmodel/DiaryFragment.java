package com.runtastic.runtasticmodel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class DiaryFragment extends Fragment {

    EditText eventDescription;
    TextView val_1;

    View myView;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.diary_layout, container, false);


        return myView;

        //val_1 = findViewById(R.id.val_1);



    }
    public void validateDescription (String eventDescription) {
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCaser = Pattern.compile("[a-z]");
        Pattern digitCase = Pattern.compile("[0-9]");


        if (eventDescription.length() < 8) {
            val_1.setTextColor(Color.RED);
        }
    }
}


