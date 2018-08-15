package com.example.android.popularmovies.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Kundan on 13-06-2018.
 * Using a model view Factory to create instance of viewModel
 */
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public MovieViewModelFactory(){
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel();
    }
}
