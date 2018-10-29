package com.runtastic.runtasticmodel;

/********************************************
 * RunTracker.java
 * S3427251 - Aaron Nettelbeck 10/18
 * For runtastic project
 * Realm database table object - defines saved run data
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RunTracker extends RealmObject {
    @PrimaryKey
    private int rid;

    private User user;
    private double timeTaken;
    private double averageSpeed;
    private double maxSpeed;
    private double distance;
    private double estimatedCalories;

    private String date;
    private RealmList<LatLong> coords = new RealmList<>();

    public void setRid(int _rid){ rid = _rid;}
    public int getRid() { return rid;}

    public void setUser(User _user) { user = _user; }
    public User getUser() { return user; }

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

    public void addCoord(LatLong _coord) { coords.add(_coord); }
    public void addCoords(RealmList<LatLong> _coords) { coords = _coords; }
    public RealmList<LatLong> getCoords() { return coords; }

    public RunTracker(){    }

    /*
    public void addCoords(List<LatLong> _coords){
        if(!_coords.isEmpty()) {
            ListIterator<LatLong> itr = _coords.listIterator();
            while(itr.hasNext()){
                coords = itr.next().toString() + ";";
            }
        }
    }
    */

    /*
    public List<LatLong> getCoords(){
        StringTokenizer tokenizer = new StringTokenizer(coords, ";");
        List<LatLong> retCoords = new ArrayList<LatLong>();
        while (tokenizer.hasMoreElements()){
             retCoords.add(new LatLong(tokenizer.nextToken()));
        }
        return retCoords;
    }
    */

}
