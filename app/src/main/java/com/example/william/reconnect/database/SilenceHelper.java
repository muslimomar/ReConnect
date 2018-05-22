package com.example.william.reconnect.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.william.reconnect.database.SilenceContract.SilenceTimeSpentEntry;

/**
 * Created by Mahmoud on 5/21/2018.
 */

public class SilenceHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "silencedaytimes.db";
    public static final int DATABASE_VERSION = 1;
    public SilenceHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES = "CREATE TABLE " + SilenceTimeSpentEntry.TABLE_NAME + " (" +
                SilenceTimeSpentEntry._ID + " INTEGER PRIMARY KEY," +
                SilenceTimeSpentEntry.COLUMN_TIME_SPENT  + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + SilenceTimeSpentEntry.TABLE_NAME);
        onCreate(db);
    }
}
