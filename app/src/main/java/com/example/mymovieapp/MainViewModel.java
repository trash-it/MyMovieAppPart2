package com.example.mymovieapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainViewModel extends AndroidViewModel {
    //private final LiveData<ArrayList<Movies>> favmovies;
    private ArrayList<Movies> favs;

    public MainViewModel(@NonNull Application application) {
        super(application);
        DBDatabase database = (DBSingelton.instance(this.getApplication()).getDatabase());
       // favmovies = (LiveData<ArrayList<Movies>>) database.userDao().loadAll();
        favs = (ArrayList<Movies>)database.userDao().loadAll();
    }

    public ArrayList<Movies> getFavmovies() {
        return favs;
        //return favmovies;
    }
}
