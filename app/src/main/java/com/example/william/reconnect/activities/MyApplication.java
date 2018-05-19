package com.example.william.reconnect.activities;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by william on 5/15/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("meditation.realm")
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
