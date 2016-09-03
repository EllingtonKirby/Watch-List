package com.example.ellioc.watchlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;

/**
 * Created by Ellioc on 9/2/2016.
 */
public class WatchListDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WatchList.db";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_YEAR = "year";
    private static final String KEY_POSTER_URL = "poster_url";
    private static final String KEY_ENTRY_TYPE = "type";
    private static final String KEY_IMDB_ID = "imdb_id";

    public WatchListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WatchListContract.WatchListEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(WatchListContract.WatchListEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addOmdbObject(OmdbObject omdbObject){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, omdbObject.getTitle());
        values.put(KEY_IMDB_ID, omdbObject.getType());
        values.put(KEY_YEAR, omdbObject.getMovieId());
        values.put(KEY_ENTRY_TYPE, omdbObject.getYear());
        values.put(KEY_POSTER_URL, omdbObject.getPosterUrl());

        sqLiteDatabase.insert(WatchListContract.WatchListEntry.TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public ArrayList<OmdbObject> getAllOmdbObjects(){
        ArrayList<OmdbObject> objects = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + WatchListContract.WatchListEntry.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OmdbObject object = new OmdbObject(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                object.setId(Integer.parseInt(cursor.getString(0)));
                objects.add(object);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return objects;
    }

    public void deleteContact(OmdbObject object) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WatchListContract.WatchListEntry.TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(object.getId()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + WatchListContract.WatchListEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
