package com.example.william.reconnect.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.adapter.ReminderAdapter;
import com.example.william.reconnect.model.Reminder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeditationsActivity extends AppCompatActivity {

    public static String TAG = MeditationsActivity.class.getSimpleName();
    public static int REMINDER_REQUEST_CODE = 20;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ArrayList<Reminder> reminders = new ArrayList<>();
    ReminderAdapter mAdapter;
    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditations);
        ButterKnife.bind(this);

        configureActionbar();

        mAdapter = new ReminderAdapter(this, reminders);
        listView.setAdapter(mAdapter);

        dummyData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            View deletedView = null;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                // edit the selected day
deletedView = view;


             // play button
                ImageView playBtn = view.findViewById(R.id.play_btn);
                playBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MeditationsActivity.this, "Play (:", Toast.LENGTH_SHORT).show();

                    }
                });

             // delete button
                ImageView deleteBtn = view.findViewById(R.id.delete_btn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter.remove(mAdapter.getItem(i));
                    }
                });

            }
        });



    }

    private void dummyData() {

        List<String> list = new ArrayList<>();
        list.add("Saturday");
        list.add("Monday");
        list.add("Friday");

        Reminder reminder = new Reminder(Reminder.TYPE_MUSIC, "12:00",list, "random","","");
        Reminder reminder2 = new Reminder(Reminder.TYPE_MUSIC, "12:00",list, "random","","");
        Reminder reminder3 = new Reminder(Reminder.TYPE_MUSIC, "12:00",list, "random","","");
        reminders.add(reminder);
        reminders.add(reminder2);
        reminders.add(reminder3);
        mAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.chakra_fab)
    public void chakraFab(View view) {
        Intent intent = new Intent(this, ReminderActivity.class);
        intent.putExtra("meditationType", "chakra");
        startActivityForResult(intent, REMINDER_REQUEST_CODE);
    }

    @OnClick(R.id.mantra_fab)
    public void mantraFab(View view) {
        Intent intent = new Intent(this, ReminderActivity.class);
        intent.putExtra("meditationType", "mantra");
        startActivityForResult(intent, REMINDER_REQUEST_CODE);
    }

    @OnClick(R.id.music_fab)
    public void musicFab(View view) {
        Intent intent = new Intent(this, ReminderActivity.class);
        intent.putExtra("meditationType", "music");
        startActivityForResult(intent, REMINDER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REMINDER_REQUEST_CODE && resultCode == RESULT_OK) {
            String reminderJson = data.getExtras().getString("reminder_json");
            Reminder reminder = new Gson().fromJson(reminderJson, Reminder.class);

            reminders.add(reminder);
            mAdapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}