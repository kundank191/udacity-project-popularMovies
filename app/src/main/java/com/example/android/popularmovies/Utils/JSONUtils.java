package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kundan on 05-06-2018.
 */
public class JSONUtils {
    private static final String RESULT_JSON_ARRAY = "results"
                                ,VOTE_COUNT = "vote_count"
                                ,VOTE_AVERAGE = "vote_average"
                                ,MOVIE_TITLE = "title"
                                ,RELEASE_DATE = "release_date"
                                ,POSTER_PATH = "poster_path"
                                ,BACKDROP_PATH = "backdrop_path"
                                ,SYNOPSIS = "overview";

    public static List<Movie> getMovieList(JSONObject root){
        List<Movie> movieList = new LinkedList<>();
        try {
            JSONArray resultsArray = root.getJSONArray(RESULT_JSON_ARRAY);
            //Iterating through JSONArray and extracting each element
            for (int i = 0; i<resultsArray.length();i++){
                JSONObject arrayElement = resultsArray.getJSONObject(i);
                //Extracting data from the arrayElement
                String movieTitle = arrayElement.getString(MOVIE_TITLE);
                String releaseDate = arrayElement.getString(RELEASE_DATE);
                Integer voteCount = arrayElement.getInt(VOTE_COUNT);
                Double voteAverage = arrayElement.getDouble(VOTE_AVERAGE);
                String posterPath = arrayElement.getString(POSTER_PATH);
                String backdropPath = arrayElement.getString(BACKDROP_PATH);
                String synopsis = arrayElement.getString(SYNOPSIS);
                //Storing all this data in a list of Movies
                movieList.add(new Movie(movieTitle,synopsis,backdropPath,posterPath,voteAverage,voteCount,releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            movieList = null;
        }
        return movieList;
    }
}
