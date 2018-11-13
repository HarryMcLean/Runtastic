package com.runtastic.runtasticmodel.fragments;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.runtastic.runtasticmodel.R;
        import com.runtastic.runtasticmodel.helpers.WeatherMap;
        import com.runtastic.runtasticmodel.realm.DiaryData;
        import com.runtastic.runtasticmodel.realm.LatLong;
        import com.runtastic.runtasticmodel.realm.RealmController;
        import com.runtastic.runtasticmodel.realm.RunTracker;

        import java.math.RoundingMode;
        import java.text.DecimalFormat;

        import io.realm.RealmList;


public class StartFragment extends Fragment {

    View myView;
    private RealmController rControl = new RealmController();
    private BroadcastReceiver broadcastReceiver;

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
        final TextView minTemp = myView.findViewById(R.id.minTempView);
        final TextView maxTemp = myView.findViewById(R.id.maxTempView);
        final TextView city = myView.findViewById(R.id.cityView);
        final TextView curTemp = myView.findViewById(R.id.tempView);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        lastRun.setText("Your last run was on " + rControl.getLastRunTrack().getDat());

        distance.setText(df.format(rControl.getLastRunTrack().getDistance()) + " km");
        calories.setText(df.format(rControl.getLastRunTrack().getEstimatedCalories()) + " kCal");
        time.setText(df.format(rControl.getLastRunTrack().getTimeTaken()) + " sec");
        speed.setText(df.format(rControl.getLastRunTrack().getAverageSpeed()) + " min/km");

        rControl.realmClose();

        if(broadcastReceiver == null) {
            Log.e("Test", "New receiver");
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        WeatherMap weather = new WeatherMap();
                        weather.getWeather(intent.getExtras().get("coord").toString(), curTemp, minTemp, maxTemp, city);

                    } catch (Exception e) {

                    }
                }
            };
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("locationUpdate"));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(broadcastReceiver != null){
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }
}
