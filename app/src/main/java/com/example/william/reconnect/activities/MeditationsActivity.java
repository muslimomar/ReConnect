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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.william.reconnect.R;
import com.example.william.reconnect.adapter.ReminderAdapter;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.util.Extras;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.example.william.reconnect.util.Extras.EXTRA_ID;

public class MeditationsActivity extends AppCompatActivity {

    public static String TAG = MeditationsActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ReminderAdapter mAdapter;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.empty_view) RelativeLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditations);
        ButterKnife.bind(this);

        configureActionbar();

        mAdapter = new ReminderAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MeditationsActivity.this, AddReminderActivity.class);
                String id = mAdapter.getItem(i).getId();
                intent.putExtra(EXTRA_ID, id);
                startActivity(intent);
            }
        });

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
        Intent intent = new Intent(this, AddReminderActivity.class);
        intent.putExtra("meditationType", Reminder.TYPE_CHAKRA);
        startActivity(intent);
    }

    @OnClick(R.id.mantra_fab)
    public void mantraFab(View view) {
        Intent intent = new Intent(this, AddReminderActivity.class);
        intent.putExtra("meditationType", Reminder.TYPE_MANTRA);
        startActivity(intent);

    }

    @OnClick(R.id.music_fab)
    public void musicFab(View view) {
        Intent intent = new Intent(this, AddReminderActivity.class);
        intent.putExtra("meditationType", Reminder.TYPE_MUSIC);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Realm realm = Realm.getDefaultInstance();
        mAdapter.updateReminders(realm.where(Reminder.class).findAll());

    }

}