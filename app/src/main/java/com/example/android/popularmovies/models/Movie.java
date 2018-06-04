package com.example.android.popularmovies.models;

/**
 * Created by Kundan on 03-06-2018.
 * A movie object which will store all the data related to a movie
 * It also has all the getter and setter methods for all the parameters
 */
public class Movie {

    private String movieName;
    private String movieSynopsis;
    private String backdropPath;
    private String posterPath;
    private Float movieRatings;
    private Integer ratingsCount;

    public Movie(String movieName, String movieSynopsis, String backdropPath, String posterPath, Float movieRatings, Integer ratingsCount) {
        this.movieName = movieName;
        this.movieSynopsis = movieSynopsis;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.movieRatings = movieRatings;
        this.ratingsCount = ratingsCount;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

    public Float getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(Float movieRatings) {
        this.movieRatings = movieRatings;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
}
