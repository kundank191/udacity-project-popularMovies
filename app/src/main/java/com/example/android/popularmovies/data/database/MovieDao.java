package com.example.android.popularmovies.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Kundan on 15-08-2018.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM favourites")
    LiveData<List<MovieEntry>> loadAllFavMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavMovie(MovieEntry movie);

    @Delete
    void removeFavMovie(MovieEntry movie);

}
