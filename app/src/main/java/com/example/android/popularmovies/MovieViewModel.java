package com.example.android.popularmovies;

import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.models.Movie;

import java.util.List;

/**
 * Created by Kundan on 09-06-2018.
 */
public class MovieViewModel extends ViewModel {

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    private List<Movie> movieList = null;
}
