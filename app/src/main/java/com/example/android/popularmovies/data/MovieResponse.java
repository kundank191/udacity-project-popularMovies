package com.example.android.popularmovies.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.Utils.JSONUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kundan on 03-06-2018.
 * A movie object which will store all the data related to a movie
 * It also has all the getter and setter methods for all the parameters
 * It implements Serializable so that it can be passed with intents
 */
@Entity(tableName = "favourite")
public class MovieResponse implements Serializable {

    @SerializedName(JSONUtils.MOVIE_TITLE)
    private String movieTitle;
    @SerializedName(JSONUtils.SYNOPSIS)
    private String movieSynopsis;
    @SerializedName(JSONUtils.BACKDROP_PATH)
    private String backdropPath;
    @SerializedName(JSONUtils.POSTER_PATH)
    private String posterPath;
    @SerializedName(JSONUtils.VOTE_AVERAGE)
    private Double voteAverage;
    @SerializedName(JSONUtils.VOTE_COUNT)
    private Integer voteCount;
    @SerializedName(JSONUtils.RELEASE_DATE)
    private String releaseDate;
    @PrimaryKey
    @NonNull
    @SerializedName(JSONUtils.MOVIE_ID)
    private String movieID;

    //Added a constructor so that this object can be created using movieID
    public MovieResponse(@NonNull String movieID){
        this.movieID = movieID;
    }

    @NonNull
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(@NonNull String movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public void setMovieSynopsis(String movieSynopsis) {
        this.movieSynopsis = movieSynopsis;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getVoteAverageOutOfFive(){
        return voteAverage/2;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
