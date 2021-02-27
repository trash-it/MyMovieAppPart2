package com.example.mymovieapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DBEntityFavorite.class}, version = 1)
    public abstract  class DBDatabase extends RoomDatabase {
    public abstract DBDaoFavorite userDao();
}
