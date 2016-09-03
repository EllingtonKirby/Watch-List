package com.example.ellioc.watchlist;

import android.provider.BaseColumns;

/**
 * Created by Ellioc on 9/2/2016.
 */
public final class WatchListContract {

    private WatchListContract(){}

    public static class WatchListEntry implements BaseColumns{
        public static final String TABLE_NAME = "watch_list";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_POSTER_URL = "poster_url";
        public static final String COLUMN_NAME_ENTRY_TYPE = "type";
        public static final String COLUMN_NAME_IMDB_ID = "imdb_id";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + WatchListEntry.TABLE_NAME + " (" +
                        WatchListEntry._ID + " INTEGER PRIMARY KEY," +
                        WatchListEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        WatchListEntry.COLUMN_NAME_ENTRY_TYPE + TEXT_TYPE + COMMA_SEP +
                        WatchListEntry.COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
                        WatchListEntry.COLUMN_NAME_IMDB_ID + TEXT_TYPE + COMMA_SEP +
                        WatchListEntry.COLUMN_NAME_POSTER_URL + TEXT_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + WatchListEntry.TABLE_NAME;
    }
}
