package com.devcora.android.reconnect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.devcora.android.reconnect.R;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {


    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Fabric.with(this, new Crashlytics());
        pref = SplashActivity.this.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        //Will handle the splash delay and check if the user is logged in or not
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.getBoolean("IS_LOGIN", true)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2200);

    }
}
