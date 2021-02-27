package com.example.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
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
    Button favButton;

    /**
     * The Movie Array List
     */
    public static ArrayList<Movies> movies;
    private int position;
    public static int trailerLenght;
    public static String[] trailerKeys = new String[4];
    public static String[] trailerNames = new String[4];

    public static String[] reviewAuthors = new String[10];
    public static String[] reviews = new String[10];
    private DBDatabase database;




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
            favButton = (Button)findViewById(R.id.buttonFavorites);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    onFavButtonClicked();
                }
            });
            new getTrailersTask().execute();
            new getReviewsTask().execute();



        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }


    }

    private void onFavButtonClicked() {
        final DBEntityFavorite favorite = new DBEntityFavorite(movies.get(position).getId(), movies.get(position).getTitle());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                database.userDao().insertAll(favorite);
            }
        };

        Log.d(TAG, "MyLog doInBackground onFavButtonClicked");

    }


    private void addTrailers() {
        final LinearLayout layoutTrailerList = (LinearLayout)findViewById(R.id.llTrailerList);
        Log.d(TAG, "MyLog doInBackground addTrailers Start" + trailerKeys.length + trailerKeys[0]);
        Log.d(TAG, "MyLog doInBackground addTrailers Start2 " + trailerNames.length + trailerNames[0]);
        for (int i = 0; i < trailerLenght ; i++ ) {
            Log.d(TAG, "MyLog doInBackground addTrailers trailer Nummer: " + i + " Name: " + trailerNames[i]);
            Button button = new Button(this);
            button.setText(trailerNames[i]);
            final String youtubeLink = "https://www.youtube.com/watch?v=" + trailerKeys[i];
            Log.d(TAG, "MyLog doInBackground youtubelink:  " + youtubeLink);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri youtubeUri = Uri.parse( youtubeLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, youtubeUri );
                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
            layoutTrailerList.addView(button);
            ;
        }
    }

    private void addReviews() {
        final LinearLayout layoutReviewList = (LinearLayout)findViewById(R.id.llreviewList);

        for (int i = 0; i < reviews.length; i++){
            if(reviewAuthors[i] != null) {
                TextView tvAuthor = new TextView(this);
                tvAuthor.setText("Author: " + reviewAuthors[i]);
                tvAuthor.setTypeface(null, Typeface.BOLD_ITALIC);
                tvAuthor.setTextSize(20);
                layoutReviewList.addView(tvAuthor);
                TextView tvReview = new TextView(this);
                tvReview.setText(reviews[i]);
                layoutReviewList.addView(tvReview);
            }
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
               String trailers =  NetworkUtils.getTrailerStream(trailerRequestUrl);
                Log.d(TAG, "MyLog doInBackground: TrailerStream"
                        + trailers);
                NetworkUtils.parseTrailerJson(trailers);
                Log.d(TAG, "MyLog doInBackground: Trailer Names und Keys " +trailerKeys[0] + trailerNames[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            addTrailers();
        }
    }


    public class getReviewsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String reviewRequestUrl = NetworkUtils.createReviewrUri(String.valueOf(movies.get(position).getId()));
            Log.d(TAG, "MyLog getReviewsTask Review ULR"
                    + reviewRequestUrl);
            try {
                String reviewStream = NetworkUtils.getReviewStream(reviewRequestUrl);
                Log.d(TAG, "MyLog getReviewsTask Review ReviewString: " + reviewStream);
                NetworkUtils.parseReviewStream(reviewStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            addReviews();
        }
    }


}
