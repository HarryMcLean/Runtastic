package com.runtastic.runtasticmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RunTracker extends RealmObject {
    @PrimaryKey
    private int rid;

    private int uid;
    private double timeTaken;
    private double averageSpeed;
    private double maxSpeed;
    private double distance;
    private double estimatedCalories;

    private String date;
    private String coords;

    public void setRid(int _rid){ rid = _rid;}
    public int getRid() { return rid;}

    public void setUid(int _uid) { uid = _uid; }
    public int getUid() { return uid; }

    public void setTimeTaken(double _time) { timeTaken = _time;}
    public double getTimeTaken() { return timeTaken; }

    public void setAverageSpeed(double _speed) { averageSpeed = _speed;}
    public double getAverageSpeed() { return averageSpeed;}

    public void setDistance(double _distance) { distance = _distance; }
    public double getDistance() { return distance; }

    public void setDat(String _date){ date = _date;}
    public String getDat() { return date;}

    public void setMaxSpeed(double _speed) { maxSpeed = _speed; }
    public double getMaxSpeed(){ return maxSpeed; }

    public void setEstimatedCalories(double _calories) { estimatedCalories = _calories; }
    public double getEstimatedCalories() { return estimatedCalories; }

    public void addCoords(List<LatLong> _coords){
        if(!_coords.isEmpty()) {
            ListIterator<LatLong> itr = _coords.listIterator();
            while(itr.hasNext()){
                coords = itr.next().toString() + ";";
            }
        }
    }

    public List<LatLong> getCoords(){
        StringTokenizer tokenizer = new StringTokenizer(coords, ";");
        List<LatLong> retCoords = new ArrayList<LatLong>();
        while (tokenizer.hasMoreElements()){
             retCoords.add(new LatLong(tokenizer.nextToken()));
        }
        return retCoords;
    }

}
