package com.example.william.reconnect.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.william.reconnect.util.Extras.EXTRA_ID;

public class AlarmScheduler {

    public void setRepeatAlarm(Context context, long alarmTimestamp, String id, long repeatTime, int requestCode) {

        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        Intent action = new Intent(context, ReminderAlarmService.class);
        action.putExtra(EXTRA_ID, id);

        PendingIntent operation = PendingIntent.getService(context, requestCode, action, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTimestamp, repeatTime, operation);

        Log.d("AlarmScheduler", "setRepeatAlarm: " + id + "\n" + requestCode + "\n" + repeatTime + "\n" + alarmTimestamp);


    }

    public void cancelAlarm(Context context, String id, int requestCode) {
        try {

            AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

            Intent action = new Intent(context, ReminderAlarmService.class);
            action.putExtra(EXTRA_ID, id);

            PendingIntent operation = PendingIntent.getService(context, requestCode, action, PendingIntent.FLAG_UPDATE_CURRENT);

            manager.cancel(operation);
            Log.d("AlarmScheduler", "Cancel Alarm: " + id + "\n" + requestCode);

        }catch (Exception e) {
         e.printStackTrace();
        }

    }
}