package com.example.william.reconnect.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

import static com.example.william.reconnect.util.Extras.EXTRA_ID;
import static com.example.william.reconnect.util.Extras.REMINDER_TYPE;

public class AlarmScheduler {

    public void setRepeatAlarms(Context context, ArrayList<Long> timeStampList, long repeatTime, ArrayList<Integer> requestCodeList, int reminderType) {

        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.putExtra(REMINDER_TYPE, reminderType);

        for (int i = 0; i < requestCodeList.size(); i++) {
            PendingIntent operation = PendingIntent.getService(context, requestCodeList.get(i),
                    action, PendingIntent.FLAG_UPDATE_CURRENT);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, timeStampList.get(i), repeatTime, operation);

        }

        Log.d("AlarmScheduler", "saveReminder: request codes: " + requestCodeList);
        Log.d("AlarmScheduler", "saveReminder: time stamps: " + timeStampList);
        Log.d("AlarmScheduler", "saveReminder: Reminder Type: " + reminderType);

    }

    public void cancelAlarms(Context context , ArrayList<Integer> requestCodeList, int reminderType) {
        try {
            AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

            Intent action = new Intent(context, ReminderAlarmService.class);
            action.putExtra(REMINDER_TYPE, reminderType);

            for (int i = 0; i < requestCodeList.size(); i++) {
                PendingIntent operation = PendingIntent.getService(context, requestCodeList.get(i), action, PendingIntent.FLAG_UPDATE_CURRENT);
                manager.cancel(operation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAlarm(Context context, String id, int requestCode) {
        try {
            AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

            Intent action = new Intent(context, ReminderAlarmService.class);
            action.putExtra(EXTRA_ID, id);

            PendingIntent operation = PendingIntent.getService(context, requestCode, action, PendingIntent.FLAG_UPDATE_CURRENT);

            manager.cancel(operation);
            Log.d("AlarmScheduler", "Cancel Alarm: " + id + "\n" + requestCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setAlarm(Context context, long alarmTimestamp, String id, int requestCode) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        Intent action = new Intent(context, ReminderAlarmService.class);
        action.putExtra(EXTRA_ID, id);

        PendingIntent operation = PendingIntent.getService(context, requestCode, action, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 23) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimestamp, operation);

        } else if (Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTimestamp, operation);

        } else {
            manager.set(AlarmManager.RTC_WAKEUP, alarmTimestamp, operation);
        }

    }
    public void setRepeatAlarm(Context context, long alarmTimestamp, String id, long repeatTime, int requestCode) {

        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        Intent action = new Intent(context, ReminderAlarmService.class);
        action.putExtra(EXTRA_ID, id);

        PendingIntent operation = PendingIntent.getService(context, requestCode, action, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTimestamp, repeatTime, operation);

        Log.d("AlarmScheduler", "setRepeatAlarm: " + id + "\n" + requestCode + "\n" + repeatTime + "\n" + alarmTimestamp);

    }
}