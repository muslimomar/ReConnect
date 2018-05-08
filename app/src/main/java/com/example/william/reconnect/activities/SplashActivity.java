package com.example.william.reconnect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.william.reconnect.LoginActivity;
import com.example.william.reconnect.R;

public class SplashActivity extends AppCompatActivity {


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = SplashActivity.this.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.getBoolean("IS_LOGIN", false)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        }, 3000);

    }
}
