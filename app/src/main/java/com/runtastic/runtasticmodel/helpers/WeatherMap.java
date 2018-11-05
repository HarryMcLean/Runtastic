package com.runtastic.runtasticmodel.helpers;

/********************************************
 * RealmController.java
 * S3427251 - Aaron Nettelbeck 10/18
 * Based on code example for openweathermap api
 * TODO: further refinement for project.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.runtastic.runtasticmodel.realm.LatLong;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherMap {
    private LatLong latlong;

    private String city;

    private JSONObject data = null;

    public WeatherMap(){
        this.latlong = new LatLong(1.1, 1.1);
        this.city = "Adelaide";
    }

    public void getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=becfa035bc0724cf6367505299a18398");

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
                    Log.e("my weather received",data.toString());
                }

            }
        }.execute();

    }
}
