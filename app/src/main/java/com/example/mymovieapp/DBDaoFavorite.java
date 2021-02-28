package com.example.mymovieapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DBDaoFavorite {
    @Query("Select * FROM Movies")
    List<Movies> loadAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movies favorite);

    @Update
    void updateAll(Movies... favorite);

    @Delete
    void delete(Movies... favorite);
}
