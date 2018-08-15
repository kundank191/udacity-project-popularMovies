package com.example.android.popularmovies.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Kundan on 29-06-2018.
 */
@Entity(tableName = "favourites")
public class MovieEntry {

    @PrimaryKey
    @NonNull
    private String movieID;
    private String posterImageURL;

    public MovieEntry(@NonNull String movieID, String posterImageURL) {
        this.movieID = movieID;
        this.posterImageURL = posterImageURL;
    }

    @NonNull
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(@NonNull String movieID) {
        this.movieID = movieID;
    }

    public String getPosterImageURL() {
        return posterImageURL;
    }

    public void setPosterImageURL(String posterImageURL) {
        this.posterImageURL = posterImageURL;
    }

}
