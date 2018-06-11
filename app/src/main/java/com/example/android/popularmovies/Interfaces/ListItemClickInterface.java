package com.example.android.popularmovies.Interfaces;

import android.view.View;

import com.example.android.popularmovies.models.Movie;

/**
 * Created by Kundan on 11-06-2018.
 */
public interface ListItemClickInterface {
    void onListItemClicked(String key, Movie movie, View itemView);
}
