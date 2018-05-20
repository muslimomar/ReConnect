package com.example.william.reconnect.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.william.reconnect.R;
import com.example.william.reconnect.activities.PlayingChakraActivity;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.util.Extras;

import io.realm.Realm;

import static com.example.william.reconnect.util.Extras.*;


/**
 * Created by William on 5/20/18.
 */

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 42;
    String reminderType = "";
    int chakraIcon;
    Reminder reminder;
    String reminderTitle = "";

    public ReminderAlarmService() {
        super(TAG);
    }

    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, String id) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.putExtra(EXTRA_ID, id);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String id = intent.getExtras().getString(EXTRA_ID);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        reminder = realm.where(Reminder.class).equalTo(EXTRA_ID, id).findFirst();
        realm.commitTransaction();

        //Display a notification to view the task details
        Intent action = setIntent();
        action.putExtra(EXTRA_ID, id);

        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        if (reminder != null) {
            getReminderType(reminder);
        }

        String reminderDescription = "You have a " + reminderType + " meditation session";

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(reminderType)
                .setContentText(reminderDescription)
                .setSmallIcon(chakraIcon)
                .setContentIntent(operation)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }

    private Intent setIntent() {
        Intent intent = null;
        // intent to Chakra
        if (reminder.getReminderType() == Reminder.TYPE_CHAKRA) {
             intent = new Intent(this,PlayingChakraActivity.class);
             intent.putExtra("music_type", reminder.getMusicPlaybackType());
             intent.putExtra("chakra_type", reminder.getChakraPlaybackType());
        }
        // intent to Mantra Activity
        if (reminder.getReminderType() == Reminder.TYPE_MANTRA) {
             intent = new Intent(this,PlayingChakraActivity.class);

        }
        // intent to Music Activity
        if (reminder.getReminderType() == Reminder.TYPE_MUSIC) {
             intent = new Intent(this,PlayingChakraActivity.class);
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


}