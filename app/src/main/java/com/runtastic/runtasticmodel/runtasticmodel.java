package com.runtastic.runtasticmodel;

import android.app.Application;
import io.realm.Realm;

public class runtasticmodel extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}