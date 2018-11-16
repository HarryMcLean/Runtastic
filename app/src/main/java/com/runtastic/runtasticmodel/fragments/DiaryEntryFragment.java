package com.runtastic.runtasticmodel.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.DiaryData;
import com.runtastic.runtasticmodel.realm.RealmController;

import java.util.Calendar;


public class DiaryEntryFragment extends Fragment {
    View myView;
    private RealmController rControl = new RealmController();

    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener1;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener2;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.diary_entry, container, false);


        final Button button = myView.findViewById(R.id.button6);
        final FragmentManager fragMan = getFragmentManager();
        final TextView startTime = myView.findViewById(R.id.textView29);
        final TextView endTime = myView.findViewById(R.id.textView30);
        final TextView startDate = myView.findViewById(R.id.textView28);
        final EditText location = myView.findViewById(R.id.textView31);
        final EditText description = myView.findViewById(R.id.editText9);

        startTime.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        endTime.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        startDate.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Calendar cal = Calendar.getInstance();


        String nowTime = cal.get(Calendar.HOUR) + ":" + minuteString(cal.get(Calendar.MINUTE)) + " " + amOrPm(cal);
        String nowDate = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);

        startTime.setText(nowTime);
        endTime.setText(nowTime);
        startDate.setText(nowDate);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Fragment frag = fragMan.findFragmentByTag(LOGOUT_TAG);
                Fragment frag = new DiaryFragment();

                rControl.saveDiaryEntry(new DiaryData(rControl.nextDiaryId(),
                        startTime.getText().toString(), endTime.getText().toString(),
                        startDate.getText().toString(), location.getText().toString(),
                        description.getText().toString()));

                rControl.realmClose();

                fragMan.beginTransaction().
                        replace(R.id.fragment_container, frag)
                        .addToBackStack(null).commit();
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month += 1;
                startDate.setText(day + "/" + month + "/" + year);
            }
        };

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener1,
                        hour, minute, false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute){
                startTime.setText(hours24to12(hour) + " " + minuteString(minute) + " " + amOrPm(hour));
            }
        };

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener2,
                        hour, minute, false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute){
                endTime.setText(hours24to12(hour) + " " + minuteString(minute) + " " + amOrPm(hour));
            }
        };

        return myView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private String amOrPm(Calendar _cal){
        if(_cal.get(Calendar.AM_PM) == 0){
            return "AM";
        }
        else
            return "PM";
    }

    private int hours24to12(int _hour){
        if(_hour - 12 > 0){
            return _hour - 12;
        }
        else return _hour;
    }
    private String amOrPm(int _hour){
        if(_hour - 12 > 0){
            return "PM";
        }
        else return "AM";
    }

    private String minuteString(int _min){
        if(_min < 10){
            return "0" + String.valueOf(_min);
        }
        else return String.valueOf(_min);
    }
}
