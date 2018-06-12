package com.example.android.popularmovies.models;

import java.io.Serializable;

/**
 * Created by Kundan on 03-06-2018.
 * A movie object which will store all the data related to a movie
 * It also has all the getter and setter methods for all the parameters
 * It implements Serializable so that it can be passed with intents
 */
public class Movie implements Serializable {

    private String movieTitle;
    private String movieSynopsis;
    private String backdropPath;
    private String posterPath;
    private Double voteAverage;
    private Integer voteCount;
    private String releaseDate;

    public Movie(String movieName, String movieSynopsis, String backdropPath, String posterPath, Double movieRatings, Integer ratingsCount, String releaseDate) {
        this.movieTitle = movieName;
        this.movieSynopsis = movieSynopsis;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.voteAverage = movieRatings;
        this.voteCount = ratingsCount;
        this.releaseDate = releaseDate;
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
