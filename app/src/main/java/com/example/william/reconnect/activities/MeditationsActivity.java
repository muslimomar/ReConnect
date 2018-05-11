package com.example.william.reconnect.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.adapter.ReminderAdapter;
import com.example.william.reconnect.model.Reminder;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeditationsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<Reminder> reminders = new ArrayList<>();
    ReminderAdapter mAdapter;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.chakra_fab)
    FloatingActionButton chakraFab;
    @BindView(R.id.mantra_fab)
    FloatingActionButton mantraFab;
    @BindView(R.id.music_fab)
    FloatingActionButton musicFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditations);
        ButterKnife.bind(this);

        configureActionbar();

        prepareRemindersArrayList();

        mAdapter = new ReminderAdapter(this, reminders);
        listView.setAdapter(mAdapter);

    }

    private void prepareRemindersArrayList() {
        reminders.add(new Reminder(Reminder.TYPE_CHAKRA, "12:00", "Sat, Sun", "The Final Scene"));
        reminders.add(new Reminder(Reminder.TYPE_MANTRA, "02:00", "Sat, Mon", "The Mantra"));
        reminders.add(new Reminder(Reminder.TYPE_MUSIC, "02:00", "Sat, Mon", "The Mantra"));
        reminders.add(new Reminder(Reminder.TYPE_MANTRA, "02:00", "Sat, Mon", "The Mantra"));

    }

    private void configureActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("My Meditations");
        toolbar.setTitleTextColor(Color.WHITE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.chakra_fab) public void chakraFab(View view){
        startActivity(new Intent(this, ChakraReminder.class));
    }

}