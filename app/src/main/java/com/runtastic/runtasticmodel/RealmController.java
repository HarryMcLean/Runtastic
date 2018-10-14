package com.runtastic.runtasticmodel;

import android.util.Log;

import io.realm.Realm;

public class RealmController {

    private Realm realm;

    RealmController(){
        realm = Realm.getDefaultInstance();
    }

    RealmController(Realm _realm){
        this.realm = _realm;
    }

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

    public User getUser(int _uid){
        User _user = realm.where(User.class).equalTo("uid", _uid).findFirst();
        return _user;
    }

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

    public RunTracker getRunTrack(int _rid){
        RunTracker _runTrack = realm.where(RunTracker.class).equalTo("rid", _rid).findFirst();
        return _runTrack;
    }
}
