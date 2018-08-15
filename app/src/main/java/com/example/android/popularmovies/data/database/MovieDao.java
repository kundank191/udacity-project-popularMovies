package com.example.android.popularmovies.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.data.network.MovieResponse;

import java.util.List;

/**
 * Created by Kundan on 15-08-2018.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM favourite")
    LiveData<List<MovieResponse>> loadAllFavMovie();

    @Query("SELECT * FROM favourite WHERE movieID = :movieID")
    MovieResponse checkIfExist(String movieID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavMovie(MovieResponse movie);

    @Delete
    void removeFavMovie(MovieResponse movie);

}
