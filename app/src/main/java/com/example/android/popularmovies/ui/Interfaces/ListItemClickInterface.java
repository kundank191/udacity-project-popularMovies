package com.example.android.popularmovies.ui.Interfaces;

import android.view.View;

import com.example.android.popularmovies.data.network.MovieResponse;

/**
 * Created by Kundan on 11-06-2018.
 */
public interface ListItemClickInterface {
    void onListItemClicked(String key, MovieResponse movieResponse, View itemView);
}
