package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.data.network.MovieCreditsResponse;
import com.example.android.popularmovies.data.network.MovieResponse;
import com.example.android.popularmovies.data.network.MovieReviewsResponse;
import com.example.android.popularmovies.data.network.MovieTrailersResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kundan on 05-06-2018.
 */
public class JSONUtils {
    public static final String RESULT_JSON_ARRAY = "results"
                                ,VOTE_COUNT = "vote_count"
                                ,VOTE_AVERAGE = "vote_average"
                                ,MOVIE_CAST = "cast"
                                ,MOVIE_REVIEWS = "results"
                                ,MOVIE_TRAILERS = "results"
                                ,MOVIE_ID = "id"
                                ,MOVIE_TITLE = "original_title"
                                ,RELEASE_DATE = "release_date"
                                ,POSTER_PATH = "poster_path"
                                ,BACKDROP_PATH = "backdrop_path"
                                ,SYNOPSIS = "overview";

    /**
     *
     * @param root JSON object
     * @return A movieResponse Object
     */
    public static MovieResponse getMovieObject(JSONObject root){
        MovieResponse movie = null;
        Gson gson = new Gson();
        movie = gson.fromJson(root.toString(),MovieResponse.class);
        return movie;
    }

    /**
     *
     * @param root the root JSON object
     * @return a list of movies
     */
    public static List<MovieResponse> getMovieList(JSONObject root){
        MovieResponse[] listMovies = null;
        Gson gson = new Gson();
        try {
            JSONArray moviesArray = root.getJSONArray(RESULT_JSON_ARRAY);
            listMovies = gson.fromJson(moviesArray.toString(),MovieResponse[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listMovies != null){
            return Arrays.asList(listMovies);
        }
        return null;
    }

    /**
     *
     * @param jsonResponse returned after requesting for movie credits
     * @return a list of movie cast members
     * Using the gson library
     */
    public static List<MovieCreditsResponse> getMovieCast(JSONObject jsonResponse){
        Gson gson = new Gson();
        MovieCreditsResponse[] listCast = null;
        try {
            JSONArray castList = jsonResponse.getJSONArray(MOVIE_CAST);
            listCast = gson.fromJson(castList.toString(),MovieCreditsResponse[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listCast != null) {
            return Arrays.asList(listCast);
        }
        return null;
    }

    /**
     *
     * @param jsonResponse returned after requesting for movie reviews
     * @return a list of movie reviews
     * Using the gson library
     */
    public static List<MovieReviewsResponse> getMovieReviews(JSONObject jsonResponse){
        Gson gson = new Gson();
        MovieReviewsResponse[] listReviews = null;
        try {
            JSONArray reviewList = jsonResponse.getJSONArray(MOVIE_REVIEWS);
            listReviews = gson.fromJson(reviewList.toString(),MovieReviewsResponse[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listReviews != null) {
            return Arrays.asList(listReviews);
        }
        return null;
    }

    /**
     *
     * @param jsonResponse returned after requesting for movie trailers
     * @return a list of movie trailers
     * Using the gson library
     */
    public static List<MovieTrailersResponse> getMovieTrailers(JSONObject jsonResponse){
        Gson gson = new Gson();
        MovieTrailersResponse[] listTrailers = null;
        try {
            JSONArray trailerList = jsonResponse.getJSONArray(MOVIE_TRAILERS);
            listTrailers = gson.fromJson(trailerList.toString(),MovieTrailersResponse[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (listTrailers != null) {
            return Arrays.asList(listTrailers);
        }
        return null;
    }
}
