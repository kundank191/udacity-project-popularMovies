package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.data.network.MovieCreditsResponse;
import com.example.android.popularmovies.data.network.MovieResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kundan on 05-06-2018.
 */
public class JSONUtils {
    private static final String RESULT_JSON_ARRAY = "results"
                                ,VOTE_COUNT = "vote_count"
                                ,VOTE_AVERAGE = "vote_average"
                                ,MOVIE_CAST = "cast"
                                ,MOVIE_ID = "id"
                                ,MOVIE_TITLE = "title"
                                ,RELEASE_DATE = "release_date"
                                ,POSTER_PATH = "poster_path"
                                ,BACKDROP_PATH = "backdrop_path"
                                ,SYNOPSIS = "overview";

    public static List<MovieResponse> getMovieList(JSONObject root){
        List<MovieResponse> movieResponseList = new LinkedList<>();
        try {
            JSONArray resultsArray = root.getJSONArray(RESULT_JSON_ARRAY);
            //Iterating through JSONArray and extracting each element
            for (int i = 0; i<resultsArray.length();i++){
                JSONObject arrayElement = resultsArray.getJSONObject(i);
                //Extracting data from the arrayElement
                String movieID = arrayElement.getString(MOVIE_ID);
                String movieTitle = arrayElement.getString(MOVIE_TITLE);
                String releaseDate = arrayElement.getString(RELEASE_DATE);
                Integer voteCount = arrayElement.getInt(VOTE_COUNT);
                Double voteAverage = arrayElement.getDouble(VOTE_AVERAGE);
                String posterPath = arrayElement.getString(POSTER_PATH);
                String backdropPath = arrayElement.getString(BACKDROP_PATH);
                String synopsis = arrayElement.getString(SYNOPSIS);
                //Storing all this data in a list of Movies
                movieResponseList.add(new MovieResponse(movieID,movieTitle,synopsis,backdropPath,posterPath,voteAverage,voteCount,releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            movieResponseList = null;
        }
        return movieResponseList;
    }

    public static List<MovieCreditsResponse> getMovieCast(JSONObject jsonResponse){
        Gson gson = new Gson();
        MovieCreditsResponse[] listCast = null;
        try {
            JSONArray castList = jsonResponse.getJSONArray(MOVIE_CAST);
            listCast = gson.fromJson(castList.toString(),MovieCreditsResponse[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Arrays.asList(listCast);
    }
}
