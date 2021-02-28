package com.example.mymovieapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;


public class MainViewModel extends AndroidViewModel {
    //private final LiveData<ArrayList<Movies>> favmovies;
    private LiveData<List<Movies>> liveFavs;
    private ArrayList<Movies> favs;

    public MainViewModel(@NonNull Application application) {
        super(application);
        DBDatabase database = (DBSingelton.instance(this.getApplication()).getDatabase());
       // favmovies = (LiveData<ArrayList<Movies>>) database.userDao().loadAll();
        liveFavs = (LiveData<List<Movies>>) database.userDao().loadAll();
        favs = (ArrayList<Movies>) database.userDao().loadAllDetail();
    }

    public LiveData<List<Movies>> getLiveFavmovies() {
        return liveFavs;
        //return favmovies;
    }

    public ArrayList<Movies> getFavMovies(){
        return favs;
    }

}
