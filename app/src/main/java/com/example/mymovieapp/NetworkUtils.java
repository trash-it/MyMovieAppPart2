package com.example.mymovieapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Network utils.
 */
public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static String stringMovieStream;
    ;

    private static final String base_url = "api.themoviedb.org";
    private static final String append_url = "3/discover/";
    private static final String api = "api_key";

    // Insert API Key here:
    private static final String api_key = "3ec1e190ef0cae1bbf210e2ffd767f69";

    private static final String sort_by = "sort_by";
    private static final String page = "page";
    private static final String page_number = "1";
    private static Uri.Builder uri_builder;

    /**
     * Gets uri builder.
     *
     * @param art the art
     * @return the uri builder
     */
    public static String createMovieUri(String art) {
        uri_builder = new Uri.Builder();
        uri_builder.scheme("https")
                .authority(base_url)
                .appendEncodedPath(append_url)
                .appendPath("movie")
                .appendQueryParameter(sort_by, art)
                .appendQueryParameter(api, api_key);
        //.appendQueryParameter(page, page_number);
        return uri_builder.build().toString();
    }

    public static String createTrailerUri(String id) {
        uri_builder = new Uri.Builder();
        uri_builder.scheme("https")
                .authority(base_url)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id)
                .appendPath("videos")
                .appendQueryParameter(api, api_key);
        return uri_builder.build().toString();
    }

    public static String createReviewrUri(String id) {
        uri_builder = new Uri.Builder();
        uri_builder.scheme("https")
                .authority(base_url)
                .appendPath("3")
                .appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter(api, api_key);
        return uri_builder.build().toString();
    }

    /**
     * Gets movie stream and save it to a String, which can be used for the parseMovieJson method.
     *
     * @param movieUrl the movie url
     * @return the movie stream
     * @throws IOException the io exception
     */
    public static String getMovieStream(String movieUrl) throws IOException {
        URL url = new URL(movieUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        try {
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                scanner.close();
                return null;
            }
        } finally {
            scanner.close();
            urlConnection.disconnect();
        }
    }

    public static String getTrailers(String trailerUrl) throws IOException {
        URL url = new URL(trailerUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        try {
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else {
                scanner.close();
                return null;
            }
        } finally {
            scanner.close();
            urlConnection.disconnect();
        }
    }

    /**
     * Parse movie json array list. which is generated from the getMovieStreamMethod
     *
     * @param json_movie_list the String which contains the MovieStream
     * @return the MovieArrayList
     */
    public static ArrayList<Movies> parseMovieJson(String json_movie_list) {

        Log.d(TAG, "MyLog NetworkUtils: parseMovieJson listcheck " + json_movie_list);
        try {
            ArrayList<Movies> movies = new ArrayList<Movies>();
            JSONObject jsonMovies = new JSONObject(json_movie_list);
            JSONArray resultsArray = (JSONArray) jsonMovies.get("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                Movies movie = new Movies();
                JSONObject jsonObject = resultsArray.getJSONObject(i);
                movie.setId(jsonObject.getInt("id"));
                movie.setPosterPath(jsonObject.getString("poster_path"));
                movie.setVoteAverage(jsonObject.getInt("vote_average"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setOverview(jsonObject.getString("overview"));
                movie.setTitle(jsonObject.getString("title"));
                movies.add(movie);
            }
            Log.d(TAG, "MyLog NetworkUtils: parseMovieJson resultsArray.lenght " + movies.size());
            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }





}
