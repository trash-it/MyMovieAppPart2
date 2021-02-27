package com.example.mymovieapp;

import android.graphics.Movie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBDaoFavorite {
    @Query("Select * FROM DBEntityFavorite")
    List<DBEntityFavorite> loadAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DBEntityFavorite... favorite);

    @Update
    void updateAll(DBEntityFavorite... favorite);

    @Delete
    void delete(DBEntityFavorite... favorite);
}
