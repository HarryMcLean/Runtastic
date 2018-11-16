package com.runtastic.runtasticmodel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.RealmController;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarFragment extends Fragment {

    View myView;

    private RealmController rControl = new RealmController();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.calendar_layout,container,false);

        final MCalendarView calendar = (MCalendarView) myView.findViewById(R.id.calendarView);

        for(int x = 0; x < rControl.getDiary(rControl.getLoggedInUser()).size(); x++){
            calendar.markDate(rControl.getDiary(rControl.getLoggedInUser()).get(x).toDateData());
        }

        calendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                String desc = null;
                String time = null;
                for(int x = 0; x < rControl.getDiary(rControl.getLoggedInUser()).size(); x++){
                    if(rControl.getDiary(rControl.getLoggedInUser()).get(x).matchDate(date)){
                        desc = rControl.getDiary(rControl.getLoggedInUser()).get(x).getDetails();
                        time = rControl.getDiary(rControl.getLoggedInUser()).get(x).getStartTime();
                    }
                }
                if(desc != null) {


                    Toast.makeText(getActivity(), time + " " + desc, Toast.LENGTH_LONG).show();
                }
            }
        });
        return myView;
    }


}
