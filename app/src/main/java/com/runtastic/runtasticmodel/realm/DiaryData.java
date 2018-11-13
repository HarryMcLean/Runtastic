package com.runtastic.runtasticmodel.realm;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DiaryData extends RealmObject {

    @PrimaryKey
    private int did;

    private String startTime;
    private String endTime;
    private String eventDate;
    private String details;

    public DiaryData() {}

    public DiaryData(int _did, String _start, String _end, String _date, String _details){
        startTime = _start;
        endTime = _end;
        eventDate = _date;
        did = _did;
        details = _details;
    }


    public String getDetails() { return details; }
    public void setDetails(String _details){ details = _details; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String _endTime){ endTime = _endTime; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String _eventDate){ eventDate = _eventDate; }

    public String getStartTime() { return startTime;}
    public void setStartTime(String _startTime){ startTime = _startTime; }

    public int getDid() { return did; }
}
