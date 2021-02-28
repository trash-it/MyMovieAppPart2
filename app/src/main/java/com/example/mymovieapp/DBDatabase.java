package com.example.mymovieapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movies.class}, version = 2)
    public abstract  class DBDatabase extends RoomDatabase {
    public abstract DBDaoFavorite userDao();
}
