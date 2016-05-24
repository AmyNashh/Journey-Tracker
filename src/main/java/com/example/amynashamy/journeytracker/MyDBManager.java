package com.example.amynashamy.journeytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Timer;
/**
 * Created by amynashAmy on 11/04/16.
 */
public class MyDBManager {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_USER = "username";
    public static final String KEY_EVENT = "event_type";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LON = "longitude";
    public static final String KEY_DIST = "distance";
    public static final String KEY_DUR = "duration";
    public static final String KEY_TIME = "time";
    public static final String KEY_MODE = "mode";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private static final String DATABASE_NAME = "Journey";
    private static final String DATABASE_TABLE = "Journey_Details";

    private static final String DATABASE_TABLE2 = "User_Details";

    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_CREATE2 = "create table User_Details " +
            "(_id integer primary key autoincrement, "+
            "username text not null, " +"password text not null);";

    private static final String DATABASE_CREATE = "create table Journey_Details " +
            "(_id integer primary key autoincrement, " + "username text not null, "+
            "event_type double not null, " +"latitude double not null, " +"longitude double not null, " + "distance double not null, "+
            "duration double not null, " + "time date not null, " +"mode text not null);";



    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //
    public MyDBManager(Context ctx)
    {
        //
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    //
    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        //
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME,
                    null, DATABASE_VERSION);
        }


        @Override
        //
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
        }

        @Override

        //
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            // whatever is to be changed on dB structure

        }
    }   //


    //
    public MyDBManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //
    public void close()
    {
        DBHelper.close();
    }

    //
    public long insertUser(String username, String password)    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);

        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    public Cursor getMyUsers() throws android.database.SQLException {
        Cursor mCursor =

                db.query(true, DATABASE_TABLE2, new String[]{
                                KEY_ROWID,
                                KEY_USERNAME,
                                KEY_PASSWORD
                        },
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public Cursor getUsers(String username, String password) throws android.database.SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[]{
                                KEY_ROWID,
                                KEY_USERNAME,
                                KEY_PASSWORD
                        },
                        KEY_USERNAME + "=\"" + username + "\"" + " AND " + KEY_PASSWORD + "=\"" + password + "\"",
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public long insertTask(String username, int event, double lat, double lon, double duration, double distance, String date, String mode)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EVENT, event);
        initialValues.put(KEY_USER, username);
        initialValues.put(KEY_LAT, lat);
        initialValues.put(KEY_LON,lon);
        initialValues.put(KEY_DIST, distance);
        initialValues.put(KEY_DUR, duration);
        initialValues.put(KEY_TIME, date);
        initialValues.put(KEY_MODE, mode);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //
    public boolean deleteTask(long rowId)
    {
        //
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    //
    public Cursor getStarts(String username)
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_USER,
                        KEY_EVENT,
                        KEY_LAT,
                        KEY_LON,
                        KEY_DIST,
                        KEY_DUR,
                        KEY_TIME,
                        KEY_MODE
                },
                KEY_USER + "=\"" + username + "\"" + " AND " + KEY_EVENT + "=\"" + 1 + "\"", ////" order by distance desc limit 10"
                null,
                null,
                null,
                null,
                null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getStops(String username)
    {
        Cursor mCursor = db.query(true,DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_USER,
                        KEY_EVENT,
                        KEY_LAT,
                        KEY_LON,
                        KEY_DIST,
                        KEY_DUR,
                        KEY_TIME,
                        KEY_MODE
                },
                KEY_USER + "=\"" + username + "\"" + " AND " + KEY_EVENT + "=\"" + 3 + "\"", //" order by distance desc limit 10"
                null,
                null,
                null,
                null,
                null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //
    public Cursor getTask(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{
                                KEY_ROWID,
                                KEY_EVENT,
                                KEY_LAT,
                                KEY_LON,
                                KEY_DIST,
                                KEY_DUR
                        },
                        KEY_ROWID + "=" + rowId,
                        null,
                        null,
                        null,
                        null,
                        null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean Login(String username, String password) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE2 + " WHERE username=? AND password=?", new String[]{username, password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }


    public Cursor getAll(String username)
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_USER,
                        KEY_LAT,
                        KEY_LON,
                        KEY_EVENT,
                        KEY_DIST,
                        KEY_DUR,
                        KEY_TIME,
                        KEY_MODE
                },
                KEY_USER + "=\"" + username + "\"",
                null,
                null,
                null,
                null,
                null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }



}