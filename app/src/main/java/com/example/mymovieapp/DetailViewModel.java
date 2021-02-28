package com.example.mymovieapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;


public class DetailViewModel extends AndroidViewModel {
    private ArrayList<Movies> favs;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        DBDatabase database = (DBSingelton.instance(this.getApplication()).getDatabase());
        favs = (ArrayList<Movies>) database.userDao().loadAllDetail();
    }

    public ArrayList<Movies> getFavmovies() {
        return favs;
    }
}
