package com.example.william.reconnect.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dpro.widgets.WeekdaysPicker;
import com.example.william.reconnect.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SilenceDay extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.silence_day_txt)
    TextView silenceDayTxt;
    @BindView(R.id.what_silence)
    TextView whatSilence;
    @BindView(R.id.more_info)
    TextView moreInfo;
    @BindView(R.id.see_guidelines)
    TextView seeGuidelines;
    @BindView(R.id.pick_day_btn)
    Button pickDayBtn;
    @BindView(R.id.silence_pick_time)
    Button silencePickTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_day);
        ButterKnife.bind(this);


        // ...
    }


    @OnClick(R.id.see_guidelines)
    public void onSeeGuidelinesClicked() {
    }

    @OnClick(R.id.pick_day_btn)
    public void onPickDayBtnClicked() {

        Calendar newCalendar = Calendar.getInstance();

        WeekdaysPicker widget = findViewById(R.id.day_picker);

        /* Converting the arraylist to list */
        List<String> strings = widget.getSelectedDaysText();
        ArrayList<String> arraList = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            arraList.add(strings.get(i));
        }

        /* Sending Days List to SilencePlaying Activity */
        Intent intent = new Intent(SilenceDay.this, SilenceChoseSign.class);
        intent.putStringArrayListExtra("silence", arraList);
        Log.d("hooooooooo", "onCreate: " + arraList);
        startActivity(intent);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //TODO Firebase save values into silence day date table column date


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick(R.id.silence_pick_time)
    public void onViewClicked() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                //TODO Firebase save values into silence day table column time;
                Log.d("hour",String.valueOf(selectedHour));
                Log.d("hour",String.valueOf(selectedMinute));


            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
