package com.runtastic.runtasticmodel.realm;

import java.util.Calendar;
import java.util.StringTokenizer;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import sun.bob.mcalendarview.vo.DateData;

public class DiaryData extends RealmObject {

    @PrimaryKey
    private int did;

    private String startTime;
    private String endTime;
    private String eventDate;
    private String details;
    private String location;

    public DiaryData() {}

    public DiaryData(int _did, String _start, String _end, String _date, String _location, String _details){
        startTime = _start;
        endTime = _end;
        eventDate = _date;
        did = _did;
        details = _details;
        location = _location;
    }


    public String getDetails() { return details; }
    public void setDetails(String _details){ details = _details; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String _endTime){ endTime = _endTime; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String _eventDate){ eventDate = _eventDate; }

    public String getStartTime() { return startTime;}
    public void setStartTime(String _startTime){ startTime = _startTime; }

    public String getLocation() { return location; }
    public void setLocation(String _location) { location = _location; }

    public int getDid() { return did; }

    public DateData toDateData() {
            StringTokenizer tokens = new StringTokenizer(eventDate, "/");
            int day = Integer.valueOf(tokens.nextToken());
            int month = Integer.valueOf(tokens.nextToken());
            int year = Integer.valueOf(tokens.nextToken());
            return new DateData(year, month, day);
    }

    public boolean matchDate(DateData dateData){
            DateData thisDate = toDateData();
            if(dateData.getDay() == thisDate.getDay() &&
                    dateData.getMonth() == thisDate.getMonth() &&
                    dateData.getYear() == thisDate.getYear()){
                return true;
            }
            else return false;
    }
}
