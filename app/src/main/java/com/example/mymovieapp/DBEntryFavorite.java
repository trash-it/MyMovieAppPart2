package com.example.mymovieapp;



import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * The DBFavorite which will be filled with Movie types.
 */
@Entity
public class DBEntryFavorite  {
    @PrimaryKey
    private  int id;
    private  String originalTitle;
    private  String title;
    private  String overview;
    private  String releaseDate;
    private  String posterPath;
    private  int voteAverage;


    public DBEntryFavorite(int id,  String title, String originalTitle, String releaseDate, String overview, int voteAverage){
        this.id = id;
        //this.movieKey = movieKey;
        this. title  = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;

    }

    /**
     * Gets id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     * @param id the id
     */
    public  void setId(int id) {
        this.id = id;
    }

    /**
     * Gets original title.
     * @return the original title
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Sets original title.
     * @param originalTitle the original title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Gets title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets overview.
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets overview.
     * @param overview the overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Gets release date.
     * @return the release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets release date.
     * @param releaseDate the release date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets poster path.
     * @return the poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Sets poster path.
     * @param posterPath the poster path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Gets vote average.
     * @return the vote average
     */
    public int getVoteAverage() {
        return voteAverage;
    }

    /**
     * Sets vote average.
     * @param voteAverage the vote average
     */
    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }






}
