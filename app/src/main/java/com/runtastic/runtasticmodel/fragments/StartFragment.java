package com.runtastic.runtasticmodel.fragments;

        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.runtastic.runtasticmodel.R;
        import com.runtastic.runtasticmodel.realm.RealmController;
        import com.runtastic.runtasticmodel.realm.RunTracker;

        import java.math.RoundingMode;
        import java.text.DecimalFormat;



public class StartFragment extends Fragment {

    View myView;
    private RealmController rControl = new RealmController();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.start_layout,container,false);
        return myView;
    }

    @Override
    public void onResume(){
        super.onResume();

        rControl.realmOpen();

        TextView distance = myView.findViewById(R.id.textView15);
        TextView calories = myView.findViewById(R.id.textView16);
        TextView time = myView.findViewById(R.id.textView17);
        TextView speed = myView.findViewById(R.id.textView18);
        TextView lastRun = myView.findViewById(R.id.textView10);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        lastRun.setText("Your last run was on " + rControl.getLastRunTrack().getDat());

        distance.setText(df.format(rControl.getLastRunTrack().getDistance()) + " km");
        calories.setText(df.format(rControl.getLastRunTrack().getEstimatedCalories()) + " kCal");
        time.setText(df.format(rControl.getLastRunTrack().getTimeTaken()) + " sec");
        speed.setText(df.format(rControl.getLastRunTrack().getAverageSpeed()) + " min/km");

        rControl.realmClose();
    }
}
