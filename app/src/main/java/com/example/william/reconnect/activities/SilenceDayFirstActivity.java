package com.example.william.reconnect.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SilenceDayFirstActivity extends AppCompatActivity {

    public static final String TAG = SilenceDayFirstActivity.class.getSimpleName();
    @BindView(R.id.silence_day_txt)
    TextView silenceDayTxt;
    @BindView(R.id.what_silence)
    TextView whatSilence;
    @BindView(R.id.more_info)
    TextView moreInfo;
    @BindView(R.id.see_guidelines)
    TextView seeGuidelines;
    @BindView(R.id.silence_pick_time)
    Button silencePickTime;
    @BindView(R.id.day_picker)
    Button dayPicker;
    @BindView(R.id.pick_day_btn)
    Button pickDayBtn;
    @BindView(R.id.picked_date_tv)
    TextView pickedDateTv;
    @BindView(R.id.picked_time_tv)
    TextView pickedTimeTv;
    String pickedDate = "";
    String pickedTime = "";
    int pickedHours;
    int pickedMins;

    Realm realm;

    Calendar calNow;
    Calendar calSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_day);
        ButterKnife.bind(this);


        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();


        pickedTimeTv.setText(today.hour + ":" + today.minute);
        String formattedDate = String.format("%02d/%02d", today.monthDay, today.month , today.year);
        pickedDateTv.setText(today.monthDay + "/" + today.month + "/" + today.year);



        calNow = Calendar.getInstance();
        calSet = (Calendar) calNow.clone();

    }


    @OnClick(R.id.see_guidelines)
    public void onSeeGuidelinesClicked() {
        //TODO call Instructions Fragment
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R.id.day_picker, R.id.silence_pick_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.day_picker:
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);
                int day = newCalendar.get(Calendar.DAY_OF_MONTH);
                int year = newCalendar.get(Calendar.YEAR);

                DatePickerDialog mDatePicker;

                mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        pickedDate = dayOfMonth + ":" + month + ":" + year;
                        pickedDateTv.setText(pickedDate);

                        calNow.set(Calendar.YEAR, year);
                        calNow.set(Calendar.MONTH, month);
                        calNow.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }
                }, year, month, day);
                mDatePicker.show();
                break;
            case R.id.silence_pick_time:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pickedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        pickedTimeTv.setText(pickedTime);
                        pickedHours = selectedHour;
                        pickedMins = selectedMinute;

                        calSet.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calSet.set(Calendar.MINUTE, selectedMinute);
                        calSet.set(Calendar.SECOND, 0);

                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }
    }

    @OnClick(R.id.pick_day_btn)
    public void onViewClicked() {

        SaveSilence();

    }

    private void SaveSilence() {
        if (pickedTime.isEmpty() || pickedDate.isEmpty()) {
            Toast.makeText(this, "Please pick a date and time!", Toast.LENGTH_SHORT).show();
            return;
        }

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Reminder reminder = realm.where(Reminder.class)
                .equalTo("pickedHours", pickedHours)
                .equalTo("pickedMinutes", pickedMins)
                .findFirst();

        if (reminder != null) {
            Toast.makeText(SilenceDayFirstActivity.this, R.string.another_reminder_stirng, Toast.LENGTH_SHORT).show();
        } else {
            goToNextActivity();
        }

        realm.commitTransaction();

    }

    private void goToNextActivity() {

        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(this, SilenceChoseSign.class);
        intent.putExtra("timestamp", calSet.getTimeInMillis());
        intent.putExtra("hours", pickedHours);
        intent.putExtra("mins", pickedMins);
        startActivity(intent);
        finish();
        Log.d(TAG, "goToNextActivity:timestamp " + calSet.getTimeInMillis());
        Log.d(TAG, "goToNextActivity:hours " + pickedHours);
        Log.d(TAG, "goToNextActivity:mins " + pickedMins);

    }
}
