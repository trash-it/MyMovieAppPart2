package com.example.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Movie detail activity.
 */
public class MovieDetailActivity extends AppCompatActivity {

    /**
     * The Text entered give the position information, for the clicked item.
     */
    private static final String TAG = "MovieDetail";
    String textEntered;
    String[] trailers;

    /**
     * The Movie Array List
     */
    public static ArrayList<Movies> movies;
    private int position;

    /**
     * The constant MOVIE_BASE_URL.
     */
    public static final String MOVIE_BASE_URL = "https://image.tmdb.org/t/p/w185";
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvOriginalTitle;
    private TextView tvReleaseDate;
    private TextView tvAverage;
    private TextView tvPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intentActivityStarter = getIntent();

        if (intentActivityStarter.hasExtra(Intent.EXTRA_TEXT)) {
            textEntered = intentActivityStarter.getStringExtra(Intent.EXTRA_TEXT);
            position = Integer.parseInt(textEntered);
            initializeViews();
            fillDetails(position);
            new getTrailersTask().execute();

        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }


    }


    private void initializeViews() {
        ivPoster = findViewById(R.id.ivPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvOriginalTitle = findViewById(R.id.tvOriginalTitle);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvAverage = findViewById(R.id.tvAverage);
        tvPlot = findViewById(R.id.tvPlot);
    }

    private void fillDetails(int position) {
        Picasso.get().load(MOVIE_BASE_URL + movies.get(position).getPosterPath()).placeholder(R.drawable.noimage).resize(200, 300).into(ivPoster);
        tvTitle.setText(movies.get(position).getTitle());
        tvOriginalTitle.setText(movies.get(position).getOriginalTitle());
        tvReleaseDate.setText(movies.get(position).getReleaseDate());
        tvAverage.setText(String.valueOf(movies.get(position).getVoteAverage()));
        tvPlot.setText(movies.get(position).getOverview());

    }

    public class getTrailersTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String trailerRequestUrl = NetworkUtils.createTrailerUri(String.valueOf(movies.get(position).getId()));
            Log.d(TAG, "MyLog doInBackground Trailer ULR"
                    + trailerRequestUrl);
            try {
               String trailers =  NetworkUtils.getTrailers(trailerRequestUrl);
                Log.d(TAG, "MyLog doInBackground: TrailerStream"
                        + trailers);
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;
        }
    }

}
