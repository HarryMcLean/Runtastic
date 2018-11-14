package com.runtastic.runtasticmodel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.RealmController;

import sun.bob.mcalendarview.MCalendarView;

public class DiaryEntryFragment extends Fragment {
    View myView;
    private RealmController rControl = new RealmController();

    private final String DIARY_TAG = "diaryFragment";
    private final String ENTRY_TAG = "entryFragment";

    //testing
    private final String LOGOUT_TAG = "logoutFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.diary_entry, container, false);

        //final MCalendarView calendar = (MCalendarView) myView.findViewById(R.id.calendarView2);

        final Button button = myView.findViewById(R.id.button6);
        final FragmentManager fragMan = getFragmentManager();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Fragment frag = fragMan.findFragmentByTag(LOGOUT_TAG);
                Fragment frag = new DiaryFragment();


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
