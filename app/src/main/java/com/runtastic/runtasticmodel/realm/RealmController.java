package com.runtastic.runtasticmodel.realm;

/********************************************
 * RealmController.java
 * S3427251 - Aaron Nettelbeck 10/18
 * Part of runtastic project, used to provide control of the realm databases
 */

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmController {

    //this will be the reference to the realm system
    private Realm realm;

    //when created open link to database
    public RealmController(){
        realm = Realm.getDefaultInstance();
    }

    //addUser adds a new user, will throw an exception if user already exists, so catching it here to keep outside code simpler.
    public void addUser(final User _user) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(_user);
                }
            });
        }
        catch(Exception e){
            Log.e("Realm Exception","User already in DB");
        }
    }

    //Can cause Exception - must be paired with checkUser() or put in a try/catch block
    public User getUser(int _uid){
            User _user = realm.where(User.class).equalTo("uid", _uid).findFirst();
            return _user;
    }

    //Can cause Exception - must be paired with checkUser() or put in a try/catch block
    public User getUser(String _username){
            User _user = realm.where(User.class).equalTo("email", _username).findFirst();
            return _user;
    }

    //Confirms whether user exists or not in database to allow easier use of above gets
    //try to get the user, if exception user mustn't exist.
   public boolean checkUser(int _uid){
        try{
            User _user = realm.where(User.class).equalTo("uid", _uid).findFirst();
            return true;
        }
        catch(Exception e){
            Log.e("Realm Exception", "User doesnt exist.");
            return false;
        }
    }

    //Confirms whether user exists or not in database to allow easier use of above gets
    //try to get the user, if exception user mustn't exist.
    public boolean checkUser(String _username){
        try{
            User _user = realm.where(User.class).equalTo("email", _username).findFirst();
            Log.e("Controller, CheckUser()", _user.getEmail());
            return true;
        }
        catch(Exception e){
            Log.e("Controller: CheckUser()", "User doesnt exist.");
            return false;
        }
    }

    //Finds the logged in user
    public User getLoggedInUser(){
        //check the database for the first user with logged in
        //necessitates calling clearLoggedInUsers() below when restarting app as it is saved in the file
        User _user = realm.where(User.class).equalTo("loggedIn", true).findFirst();
        return _user;
    }

    //Using a realm transaction to alter the user's data as not allowed to directly change managed data
    public void loginUser(User _user){
        realm.beginTransaction();
        _user.setLoggedIn();
        realm.commitTransaction();
    }

    //Finds all the logged in users and logs them out if it was missed last run, allows getLoggedInUser()
    //to work right
    public void clearLoggedInUsers(){
        //get list of all users that have loggedIn as true
        RealmResults<User> results = realm.where(User.class).equalTo("loggedIn", true).findAll();
        //cycle through list and change value, saving each time
        for(int x = 0; x < results.size(); x++){
            User modifyUser = results.get(x);
            realm.beginTransaction();
            modifyUser.setLoggedOut();
            realm.commitTransaction();
        }
    }

    //Finds next available user id
    public int nextUserId() {
        int nextUid;
        try {
            nextUid = realm.where(User.class).max("uid").intValue() + 1;
        }
        catch (Exception e){
        nextUid = 1001;
        }
        return nextUid;
    }

    public int nextRuntrackId(){
        int nextRid;
        try {
            nextRid = realm.where(RunTracker.class).max("rid").intValue() + 1;
        }
        catch(Exception e){
            nextRid = 1001;
        }
        return nextRid;
    }

    //Adds a runtrack object to database, will throw an exception if user already exists.
    public void saveRunTrack(final RunTracker _runtrack) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    getLoggedInUser().addRuntrack(_runtrack);
                }
            });
        }
        catch(Exception e){
            Log.e("Realm Exception", "Run Track ID already exists, save aborted");
        }
    }

    //Can cause Exception - must be paired with checkRunTrack() or put in a try/catch block
    public RealmList<RunTracker> getRunTracks(User _user){
        return _user.getRuntracks();
    }

    public RunTracker getLastRunTrack(){
        return getLoggedInUser().getRuntracks().last();
    }

    public RunTracker getFastestAverage(){
        double result = realm.where(RunTracker.class).equalTo("user.uid", getLoggedInUser().getUid()).max("averageSpeed").doubleValue();
        RunTracker ret = realm.where(RunTracker.class).equalTo("user.uid", getLoggedInUser().getUid()).equalTo("averageSpeed", result).findFirst();
        return ret;
    }

    public RunTracker getFastest(){
        double result = realm.where(RunTracker.class).equalTo("user.uid", getLoggedInUser().getUid()).max("maxSpeed").doubleValue();
        RunTracker ret = realm.where(RunTracker.class).equalTo("user.uid", getLoggedInUser().getUid()).equalTo("maxSpeed", result).findFirst();
        return ret;
    }

    public RunTracker getLongest(){
        double result = realm.where(RunTracker.class).equalTo("user.uid", getLoggedInUser().getUid()).max("distance").doubleValue();
        RunTracker ret = realm.where(RunTracker.class).equalTo("user.uid", getLoggedInUser().getUid()).equalTo("distance", result).findFirst();
        return ret;
    }

    //As the controller handles all the database transactions it needs to be able to close its private realm reference.
    public void realmClose(){
        realm.close();
    }

    //Messy but in case anyone needs to reopen it.
    public void realmOpen() {
        realm = realm.getDefaultInstance();
    }
}
