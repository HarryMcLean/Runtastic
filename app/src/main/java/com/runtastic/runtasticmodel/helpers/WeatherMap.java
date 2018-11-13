package com.runtastic.runtasticmodel.helpers;

/********************************************
 * RealmController.java
 * S3427251 - Aaron Nettelbeck 10/18
 * Based on code example for openweathermap api
 * TODO: further refinement for project.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.runtastic.runtasticmodel.helpers.weather.Weather;
import com.runtastic.runtasticmodel.realm.LatLong;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class WeatherMap {
    private LatLong latlong;

    private JSONObject data = null;

    private TextView minTempView;
    private TextView maxTempView;
    private TextView cityView;
    private TextView curTemp;

    public WeatherMap(){
    }

    public void getWeather(String _gpsData, TextView _curTemp, TextView _minTemp, TextView _maxTemp, TextView _city){
        //tokenizer assumes will only ever get lat|long string from gps service
        StringTokenizer tokenizer = new StringTokenizer(_gpsData, "|");
        double lat = Double.valueOf(tokenizer.nextToken());
        double lon = Double.valueOf(tokenizer.nextToken());
        latlong = new LatLong(lat, lon);
        minTempView = _minTemp;
        maxTempView = _maxTemp;
        cityView = _city;
        curTemp = _curTemp;
        getJSON();
    }

    private void getJSON() {

        data = null;
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+latlong.getLatitude()+"&lon="+latlong.getLongitude()+"&APPID=becfa035bc0724cf6367505299a18398&units=metric");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if(data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception "+ e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if(data!=null){
                    Gson gson = new Gson();
                    Weather weather = gson.fromJson(data.toString(), Weather.class);
                    Log.e("Weather", data.toString());
                    minTempView.setText("Max Temp: " + String.valueOf(weather.getMinTemp()));
                    maxTempView.setText("Min Temp: " + String.valueOf(weather.getMaxTemp()));
                    cityView.setText(weather.getName());
                    curTemp.setText("Temp: " + String.valueOf(weather.getTemp()));
                }

            }
        }.execute();

    }
}
