package com.runtastic.runtasticmodel;

import java.util.StringTokenizer;

import io.realm.RealmObject;
//TEST: testing git branching
public class LatLong extends RealmObject {
    private Double latitude;
    private Double longitude;

    public LatLong(){
        latitude = 0.0;
        longitude = 0.0;
    }

    public LatLong(Double _lat, Double _long){
        latitude = _lat;
        longitude = _long;
    }

    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public void setLatitude(Double _lat){ latitude = _lat;}
    public void setLongitude(Double _long) { longitude = _long;}

    public String toString(){
        String latString = Double.toString(latitude);
        String longString = Double.toString(longitude);
        String retString = latString + "," + longString;
        return retString;
    }

    public LatLong(String _coordString){
        StringTokenizer tokens = new StringTokenizer(_coordString, ",");
        latitude = Double.valueOf(tokens.nextToken());
        longitude = Double.valueOf(tokens.nextToken());
    }
}
