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

    private String date;
    private String coords;

    public void setRid(int _rid){ rid = _rid;}
    public int getRid() { return rid;}

    public void setDat(String _date){ date = _date;}
    public String getDat() { return date;}

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
