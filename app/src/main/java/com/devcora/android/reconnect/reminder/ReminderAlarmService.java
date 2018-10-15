package com.devcora.android.reconnect.reminder;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.devcora.android.reconnect.R;
import com.devcora.android.reconnect.activities.MantraPlayingActivity;
import com.devcora.android.reconnect.activities.PlayingChakraActivity;
import com.devcora.android.reconnect.activities.PlayingMusicActivity;
import com.devcora.android.reconnect.model.Reminder;
import com.google.gson.Gson;

import static com.devcora.android.reconnect.util.Extras.CHAKRA;
import static com.devcora.android.reconnect.util.Extras.CHAKRA_REMINDER_OBJECT;
import static com.devcora.android.reconnect.util.Extras.MANTRA;
import static com.devcora.android.reconnect.util.Extras.MANTRA_REMINDER_OBJECT;
import static com.devcora.android.reconnect.util.Extras.MUSIC;
import static com.devcora.android.reconnect.util.Extras.MUSIC_REMINDER_OBJECT;
import static com.devcora.android.reconnect.util.Extras.PREFS_NAME;
import static com.devcora.android.reconnect.util.Extras.REMINDER_TYPE;


/**
 * Created by William on 5/20/18.
 */

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 42;
    String reminderType = "";
    int chakraIcon;
    Reminder reminder;

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int reminderTypeExtra = intent.getExtras().getInt(REMINDER_TYPE);
        reminder = getFromSharedPrefs(reminderTypeExtra);

        //Display a notification to view the task details
        Intent action = setReminderIntent();

        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        getReminderType(reminder);


        String reminderDescription = "You have a " + reminderType + " meditation session";

        NotificationCompat.Builder note = new NotificationCompat.Builder(this)
                .setContentTitle(reminderType)
                .setContentText(reminderDescription)
                .setSmallIcon(chakraIcon)
                .setContentIntent(operation)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("channel_01", "Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Get reminds about meditation sessions");
            manager.createNotificationChannel(channel);
            note.setChannelId("channel_01");
        }

        manager.notify(NOTIFICATION_ID, note.build());

//        startActivity(action);

    }

    private Intent setReminderIntent() {
        Intent intent = null;
        // intent to Chakra Activity
        if (reminder.getReminderType() == Reminder.TYPE_CHAKRA) {
            intent = new Intent(this, PlayingChakraActivity.class);
            intent.putExtra("music_type", reminder.getSoundPlaybackType());
            intent.putExtra("chakra_type", reminder.getChakraPlaybackTYpe());
        }
        // intent to Mantra Activity
        if (reminder.getReminderType() == Reminder.TYPE_MANTRA) {
            intent = new Intent(this, MantraPlayingActivity.class);
            intent.putExtra("mantra_type", reminder.getMantraPlaybackType());
            intent.putExtra("music_type", reminder.getSoundPlaybackType());
        }
        if (reminder.getReminderType() == Reminder.TYPE_MUSIC) {
            intent = new Intent(this, PlayingMusicActivity.class);
            intent.putExtra("music_type", reminder.getSoundPlaybackType());
        }


        return intent;
    }


    private void getReminderType(Reminder reminder) {

        switch (reminder.getReminderType()) {
            case Reminder.TYPE_CHAKRA:
                reminderType = CHAKRA;
                chakraIcon = R.drawable.ic_chakra_notification;
                break;
            case Reminder.TYPE_MANTRA:
                reminderType = MANTRA;
                chakraIcon = R.drawable.ic_mantra_notification;
                break;
            case Reminder.TYPE_MUSIC:
                reminderType = MUSIC;
                chakraIcon = R.drawable.ic_music_notification;
                break;
        }
    }

    private Reminder getFromSharedPrefs(int objectKey) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonObject = null;
        if (objectKey == Reminder.TYPE_CHAKRA) {
            jsonObject = sharedPreferences.getString(CHAKRA_REMINDER_OBJECT, "");
        } else if (objectKey == Reminder.TYPE_MANTRA) {
            jsonObject = sharedPreferences.getString(MANTRA_REMINDER_OBJECT, "");
        } else {
            jsonObject = sharedPreferences.getString(MUSIC_REMINDER_OBJECT, "");
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonObject, Reminder.class);
    }
}