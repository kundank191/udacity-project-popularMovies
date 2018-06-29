package com.example.android.popularmovies.ui.detail;

import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.data.network.MovieResponse;

import java.util.List;

/**
 * Created by Kundan on 09-06-2018.
 * This view model class will store data across configuration changes
 */
public class MovieViewModel extends ViewModel {

    public MovieViewModel(){

    }

    public List<MovieResponse> getMovieList() {
        return movieResponseList;
    }

    public void setMovieList(List<MovieResponse> movieResponseList) {
        this.movieResponseList = movieResponseList;
    }

    private List<MovieResponse> movieResponseList = null;
}
