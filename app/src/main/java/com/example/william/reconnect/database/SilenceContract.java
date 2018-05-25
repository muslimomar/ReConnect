package com.example.william.reconnect.database;

import android.provider.BaseColumns;

/**
 * Created by Mahmoud on 5/21/2018.
 */

public class SilenceContract {

    private SilenceContract(){}

    public static class SilenceTimeSpentEntry implements BaseColumns{
        public static final String TABLE_NAME="silencetime";
        public static final String COLUMN_TIME_SPENT="timespent";


        public interface BaseColumns
        {
            /**
             * The unique ID for a row.
             * <P>Type: INTEGER (long)</P>
             */
            public static final String _ID = "_id";

            /**
             * The count of rows in a directory.
             * <P>Type: INTEGER</P>
             */
            public static final String _COUNT = "_count";
        }

    }

}
