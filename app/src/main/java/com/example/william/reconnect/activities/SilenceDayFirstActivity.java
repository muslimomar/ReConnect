package com.example.william.reconnect.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.william.reconnect.R;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SilenceDayFirstActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_day);
        ButterKnife.bind(this);


        // ...
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
                        //TODO implement Time into Reminder class;
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

                        //TODO implement Time into Reminder class;


                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }
    }

    @OnClick(R.id.pick_day_btn)
    public void onViewClicked() {
        Intent intent = new Intent(this, SilenceChoseSign.class);
        startActivity(intent);
    }
}
