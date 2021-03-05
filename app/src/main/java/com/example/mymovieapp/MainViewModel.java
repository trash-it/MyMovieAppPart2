package com.example.mymovieapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;


/**
 * The viewmodel for the Mainactivity
 */
public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> liveFavs;
    private ArrayList<Movies> favs;

    public MainViewModel(@NonNull Application application) {
        super(application);
        DBDatabase database = (DBSingelton.instance(this.getApplication()).getDatabase());
        liveFavs = (LiveData<List<Movies>>) database.userDao().loadAll();
        favs = (ArrayList<Movies>) database.userDao().loadAllDetail();
    }

    /**
     * Retuns a livedataobject from favoriteMovies
     */
    public LiveData<List<Movies>> getLiveFavmovies() {
        return liveFavs;

    }
    /**
     * Returns favoriteMovies as a List
     */
    public ArrayList<Movies> getFavMovies(){
        return favs;
    }

}
