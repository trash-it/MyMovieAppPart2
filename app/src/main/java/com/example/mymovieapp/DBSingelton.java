package com.example.mymovieapp;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;



public class DBSingelton {
    private static final String LOG_TAG = DBSingelton.class.getSimpleName();

    private static DBSingelton instance = null;
    private static final String DBName = "FavDatasbase.db";
    private DBDatabase db;

    private DBSingelton (Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(),
                DBDatabase.class,
                DBName)
                .allowMainThreadQueries()
                .build();
        Log.d(LOG_TAG, "Creating new database instance");
    }

    public DBDatabase getDatabase(){
        return db;
    }

    public static synchronized DBSingelton instance(Context context) {
        if (instance == null) {
            instance = new DBSingelton(context);
        }
        return instance;
    }

}
