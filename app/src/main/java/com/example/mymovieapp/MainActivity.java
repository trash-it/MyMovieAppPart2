package com.example.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieViewHolder.OnItemClickListener {
    private static final String TAG = "MainActivity";

    private ProgressBar mProgressBar;
    private String MovieUrlPopulate;
    private String MovieUrlRated;
    private String postexecute;
    private String MovieStreamStringPopulate;
    private String MovieStreamStringRate;
    private ArrayList<Movies> mactuallList;
    private ArrayList<Movies> mPopularList;
    private ArrayList<Movies> mTopTopRatedList;
    private LiveData<Movies> favoriteMovieList;
    private MovieAdapter mAdapter;
    private RecyclerView rvMain;
    private GridLayoutManager glm;
    private DBDatabase database;
    private String message;


    /**
     * Creates the two Menu Buttons, Top Rated and Most Popular.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popularmenu, menu);
        getMenuInflater().inflate(R.menu.ratedmenu, menu);
        getMenuInflater().inflate(R.menu.favmenu, menu);
        return true;
    }

    /**
     * React on pressed Menu Buttons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemThatWasSelected = item.getItemId();
        Context context = MainActivity.this;
        switch(menuItemThatWasSelected){
            case R.id.menu_Rated:
            message = "Show Top Rated";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            mactuallList = mTopTopRatedList;
            reloadlist(mactuallList);
            break;

            case R.id.menu_Popular:
            message = "Show Most Popular";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            mactuallList = mPopularList;
            reloadlist(mactuallList);
            break;

            case R.id.menu_fav:
            message = "Show Favorites";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            createFavList();
            reloadlist(mactuallList);
            break;
        }
        return true;
    }

    private void createFavList() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        
        mactuallList = new ArrayList(viewModel.getFavmovies());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_main);
        mProgressBar.setVisibility(View.INVISIBLE);

        new fetch_Movies().execute();
        rvMain = findViewById(R.id.rv_MainActivity);
        rvMain.setHasFixedSize(true);
        glm = new GridLayoutManager(this, 2);
        rvMain.setLayoutManager(glm);
        setDisplaySize();
        database = DBSingelton.instance(this).getDatabase();

    }

    /**
     * Checks the Displaysize and give the information to the Movieadaper
     * the Movieadapter will use it to set the right size of the Pictures in the
     * MainActivity Views
     */
    public void setDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        MovieAdapter.itemWidth = width;
        MovieAdapter.itemhight = height;
    }

    /**
     * On load finished creates and connects the generated adapter
     */
    public void onLoadFinished() {
        mAdapter = new MovieAdapter(mactuallList, this);
        rvMain.setAdapter(mAdapter);

    }

    /**
     * Reloadlist reloads the MovieLists when one of the selection Buttons was pressed.
     * @param list the list which is transmitted from the onOptionsItemSelected method.
     */
    public void reloadlist(ArrayList<Movies> list) {
        MovieAdapter mAdapter = new MovieAdapter(list, this);
        rvMain.removeAllViewsInLayout();
        rvMain.setAdapter(mAdapter);


    }

    /**
     * this method listen on clicks on the single movie view and start the MovieDetailActivity.
     * @param Position the list which is transmitted from the onOptionsItemSelected method.
     */
    @Override
    public void onItemClick(int Position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(intent.EXTRA_TEXT, String.valueOf(Position));
        MovieDetailActivity.movies = mactuallList;
        startActivity(intent);
    }

    public DBDatabase getDatabase() {
        return database;
    }

    /**
     * fetch_Movies create the URL, load the InformationsStream change it to JSON and save all
     * movies in an ArrayList.
     */
    public class fetch_Movies extends AsyncTask<String, Void, String> {
        private static final String TAG = "fetch_movie";

        @Override
        protected String doInBackground(String... strings) {
            mPopularList = new ArrayList<>();
            mTopTopRatedList = new ArrayList<>();
            MovieUrlPopulate = NetworkUtils.createMovieUri("popularity.desc");
            MovieUrlRated = NetworkUtils.createMovieUri("vote_average.desc");
            String test = NetworkUtils.createMovieUri("vote_average.desc");
            try {
                MovieStreamStringPopulate = NetworkUtils.getMovieStream(MovieUrlPopulate);
                //  MovieStreamStringRate = NetworkUtils.getMovieStream(MovieUrlRated);
                MovieStreamStringRate = NetworkUtils.getMovieStream(test);
                Log.d(TAG, "MyLog doInBackground: getMovieStream " + " Stream Populate "
                        + MovieStreamStringPopulate);
                Log.d(TAG, "MyLog doInBackground: getMovieStream " + " Stream Rate"
                        + MovieStreamStringRate);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPopularList = NetworkUtils.parseMovieJson(MovieStreamStringPopulate);
            mTopTopRatedList = NetworkUtils.parseMovieJson(MovieStreamStringRate);
            //  Log.d(TAG, "MyLog doInBackground: parseMovieJson size Rate "
            //  + mTopTopRatedList.size());
            Log.d(TAG, "MyLog doInBackground: parseMovieJson  size Popui"
                    + mPopularList.size());
            mactuallList = mPopularList;
            return "";
        }

        //  @Override
        protected void onPostExecute(String s) {
            postexecute = s;
            Log.d(TAG, "MyLog onPostExecute" + s);
            mProgressBar.setVisibility(View.INVISIBLE);
            onLoadFinished();

        }


    }
}

