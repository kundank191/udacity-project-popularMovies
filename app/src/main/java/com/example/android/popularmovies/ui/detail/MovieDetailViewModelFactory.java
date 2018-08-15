package com.example.android.popularmovies.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.ui.list.MovieViewModel;

/**
 * Created by Kundan on 15-08-2018.
 * View Model which will be used by the detail activity
 */
public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public MovieDetailViewModelFactory(){

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel();
    }
}
