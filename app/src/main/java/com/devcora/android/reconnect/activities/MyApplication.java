package com.devcora.android.reconnect.activities;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by william on 5/15/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("meditation.realm").deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
