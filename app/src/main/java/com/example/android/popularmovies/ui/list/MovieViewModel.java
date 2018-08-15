package com.example.android.popularmovies.ui.list;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.data.database.AppDatabase;
import com.example.android.popularmovies.data.network.MovieResponse;

import java.util.List;

/**
 * Created by Kundan on 09-06-2018.
 * This view model class will store data across configuration changes
 */
public class MovieViewModel extends ViewModel {

    public MovieViewModel(){

    }

    private List<MovieResponse> movieResponseList = null;

    public List<MovieResponse> getMovieList() {
        return movieResponseList;
    }

    public void setMovieList(List<MovieResponse> movieResponseList) {
        this.movieResponseList = movieResponseList;
    }

    public LiveData<List<MovieResponse>> getFavouriteMovieList(Application application) {
        AppDatabase mDb = AppDatabase.getInstance(application);
        return mDb.movieDao().loadAllFavMovie();
    }
}
