package com.example.william.reconnect.reminder;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by delaroy on 9/22/17.
 */

public class AlarmManagerProvider {

    private static final String TAG = AlarmManagerProvider.class.getSimpleName();
    private static AlarmManager sAlarmManager;

    /*package*/ static synchronized AlarmManager getAlarmManager(Context context) {
        if (sAlarmManager == null) {
            sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return sAlarmManager;
    }

}
