package com.runtastic.runtasticmodel.realm;
/********************************************
 * User.java
 * S3427251 - Aaron Nettelbeck 10/18
 * For runtastic project
 * Realm database table object - defines user data
 */

import android.util.Log;

import java.util.Calendar;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private int uid;
    private String email;
    private String dob;
    private String password;
    private boolean loggedIn = false;
    private boolean remember = false;

    private RealmList<RunTracker> runtracks = new RealmList<>();

    public User(){
        this.uid = 0;
        this.email = "none";
        this.dob = "1999.1.1";
        this.password = "none";
    }

    public User(int _uid, String _email, String _dob, String _password){
        this.uid = _uid;
        this.email = _email;
        this.dob = _dob;
        this.password = _password;
    }

    public int getUid(){
        return uid;
    }

    public String getEmail(){
        return email;
    }

    public String getDob(){
        return dob;
    }

    public void setDob(String _dob) { dob = _dob;}

    public void setUid(int _uid) { uid = _uid; }

    public void setEmail(String _email) { email = _email; }

    public void setPassword(String _password) { password = _password; }

    public String getPassword() { return password; }

    public void setLoggedIn(){ loggedIn = true;    }
    public void setLoggedOut() { loggedIn = false; remember = false; }

    public void setRemembered(){ remember = true; }

    public boolean isRemembered() { return remember; }

    public boolean getLoggedIn() { return loggedIn; }

    protected void addRuntrack(RunTracker _inTrack){
        //need to add code to confirm unique RID as realm doesnt seem to be doing it
        boolean exists = false;
        for(RunTracker r : runtracks){
            if(r.getRid() == _inTrack.getRid()){
                exists = true;
            }
        }

        if(exists){
            Log.e("User", "RID already exists, not added");
        }
        else
            runtracks.add(_inTrack);
    }
    protected RealmList<RunTracker> getRuntracks() { return runtracks; }
}
