package com.example.william.reconnect.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.william.reconnect.LoginActivity;
import com.example.william.reconnect.R;
import com.example.william.reconnect.fragments.Balance;
import com.example.william.reconnect.fragments.Home;
import com.example.william.reconnect.fragments.Instructions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment, new Home()).commit();
        }

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        NavigationView navigationView = findViewById(R.id.nv);
        mToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        setupDrawerContent(navigationView);

        // start drawer when first starts
        mDrawerLayout.openDrawer(Gravity.LEFT);
        pref = MainActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

    }


    public void selectItemDrawer(MenuItem item) {
        Fragment myFragment = null;
        Class fragmentClass = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragmentClass = Home.class;
                break;
            case R.id.summary:
                fragmentClass = Balance.class;
                break;
            case R.id.instructions:
                fragmentClass = Instructions.class;
                break;
            case R.id.logout:
                editor.putBoolean("IS_LOGIN", false);
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();

                break;
        }

        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), "selectItemDrawer: ", e);
        }

        FragmentManager fragmentManager = getFragmentManager();
        // when logout is selected it will be null
        if (myFragment != null) {
            fragmentManager.beginTransaction().replace(R.id.fragment, myFragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());
        }
        mDrawerLayout.closeDrawers();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
