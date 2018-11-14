package com.runtastic.runtasticmodel.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;


import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.activities.SideNavBar;
import com.runtastic.runtasticmodel.helpers.DiaryRecyclerAdapter;
import com.runtastic.runtasticmodel.realm.RealmController;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class DiaryFragment extends Fragment {

    View myView;
    private RealmController rControl = new RealmController();

    private final String DIARY_TAG = "diaryFragment";
    private final String ENTRY_TAG = "entryFragment";

    //testing
    private final String LOGOUT_TAG = "logoutFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.diary_layout, container, false);

        final MCalendarView calendar = (MCalendarView) myView.findViewById(R.id.calendarView2);

        final Button button = myView.findViewById(R.id.newEntryButton);
        final FragmentManager fragMan = getFragmentManager();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Fragment frag = fragMan.findFragmentByTag(LOGOUT_TAG);
                Fragment frag = new DiaryEntryFragment();


                fragMan.beginTransaction().
                        replace(R.id.fragment_container, frag)
                        .addToBackStack(null).commit();
            }
        });

        return myView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}


