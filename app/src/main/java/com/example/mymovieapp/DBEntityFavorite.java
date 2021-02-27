package com.example.mymovieapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DBEntityFavorite {
    @PrimaryKey
    private int ID;
    private int movieKey;
    private String title;
    private String originalTitle;
    private String releaseDate;
    private String description;
    private int averageVote;
    private String plot;

    @Ignore
    public DBEntityFavorite(int ID, int movieKey, String title, String originalTitle, String releaseDate, String description, int averageVote, String plot){
        this.ID = ID;
        this.movieKey = movieKey;
        this. title  = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.description = description;
        this.averageVote = averageVote;
        this.plot = plot;
    }

    public DBEntityFavorite(int ID, String title){
        this.ID = ID;
        this. title  = title;
    }

    public int getID() {
        return ID;
    }

    public int getMovieKey() {
        return movieKey;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public int getAverageVote() {
        return averageVote;
    }

    public String getPlot() {
        return plot;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMovieKey(int movieKey) {
        this.movieKey = movieKey;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageVote(int averageVote) {
        this.averageVote = averageVote;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}


