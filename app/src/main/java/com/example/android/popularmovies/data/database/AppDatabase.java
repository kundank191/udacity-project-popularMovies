package com.example.android.popularmovies.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.popularmovies.data.network.MovieResponse;

/**
 * Created by Kundan on 15-08-2018.
 * Return an instance of APP database
 */
@Database(entities = {MovieResponse.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
   private static AppDatabase sInstance;
   private static final String DATABASE_NAME = "movies";

   public static AppDatabase getInstance(Context context){
       if (sInstance == null){
           synchronized (LOCK){
               sInstance = Room.databaseBuilder(context.getApplicationContext(),
                       AppDatabase.class,AppDatabase.DATABASE_NAME)
                       .build();
           }
       }
       return sInstance;
   }

   public abstract MovieDao movieDao();
}
