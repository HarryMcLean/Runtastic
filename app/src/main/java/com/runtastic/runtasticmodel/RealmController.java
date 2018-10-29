package com.runtastic.runtasticmodel;

/********************************************
 * RealmController.java
 * S3427251 - Aaron Nettelbeck 10/18
 * Part of runtastic project, used to provide control of the realm databases
 */

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    //this will be the reference to the realm system
    private Realm realm;

    //when created open link to database
    RealmController(){
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
            return true;
        }
        catch(Exception e){
            Log.e("Realm Exception", "User doesnt exist.");
            return false;
        }
    }

    //Finds the logged in user
    public int getLoggedInUser(){
        //check the database for the first user with logged in
        //necessitates calling clearLoggedInUsers() below when restarting app as it is saved in the file
        User _user = realm.where(User.class).equalTo("loggedIn", true).findFirst();
        Log.e("Testing", "Logged in user found?");
        return _user.getUid();
    }

    //Using a realm transaction to alter the user's data as not allowed to directly change managed data
    public void loginUser(User _user){
        realm.beginTransaction();
        _user.setLoggedIn();
        realm.commitTransaction();
        Log.e("Testing", "Logged in user set?");
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
            Log.e("Testing", "Logged in user cleared?");
        }
    }

    //Finds next available user id
    public int nextUserId(){
        int nextUid = realm.where(User.class).max("uid").intValue() + 1;
        return nextUid;
    }

    //Adds a runtrack object to database, will throw an exception if user already exists, so catching it here to keep outside code simpler.
    public void addRunTrack(final RunTracker _runtrack) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(_runtrack);
                }
            });
        }
        catch(Exception e){
            Log.e("Realm Exception", "Run Track ID already exists, save aborted");
        }
    }

    //Can cause Exception - must be paired with checkRunTrack() or put in a try/catch block
    public RunTracker getRunTrack(int _rid){
        RunTracker _runTrack = realm.where(RunTracker.class).equalTo("rid", _rid).findFirst();
        return _runTrack;
    }

    //todo:checkRunTrack()
    public boolean checkRunTrack(int _rid){
        return true;
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
