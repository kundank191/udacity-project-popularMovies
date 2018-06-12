package com.example.android.popularmovies.Utils;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android.popularmovies.Interfaces.JsonDataDownloadInterface;

import org.json.JSONObject;

/**
 * Created by Kundan on 03-06-2018.
 */
public class NetworkUtils {

    private static final String BASE_URL_POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?", BASE_URL_NOW_PLAYING_MOVIES = "https://api.themoviedb.org/3/movie/now_playing?", BASE_URL_IMAGE_POSTER = "http://image.tmdb.org/t/p/w500/", BASE_URL_IMAGE_BACKDROP = "http://image.tmdb.org/t/p/w780/", QUERY_PARAM_API_KEY = "api_key", QUERY_PARAM_LANGUAGE = "language", QUERY_PARAM_INCLUDE_ADULT = "include_adult", QUERY_PARAM_PAGE = "page", PARAM_LANGUAGE = "en-US";

    /**
     * @param imagePath Takes the path of the image in the database
     * @return the complete URI of the image path
     */
    public static String getPosterImageURL(String imagePath) {
        return BASE_URL_IMAGE_POSTER + imagePath;
    }

    /**
     * @param imagePath Takes the path of the image in the database
     * @return the complete URI of the image path
     */
    public static String getBackdropImageURL(String imagePath) {
        return BASE_URL_IMAGE_BACKDROP + imagePath;
    }

    /**
     * Downloads Popular movies list
     *
     * @param API_KEY The api key to access the online database
     */
    public static void getPopularMovieList(Context context, String API_KEY) {
        requestDataFromInternet(context, BASE_URL_POPULAR_MOVIES, API_KEY);
    }

    /**
     * Downloads Now Playing movies list
     *
     * @param API_KEY The api key to access the online database
     */
    public static void getNowPlayingMovieList(Context context, String API_KEY) {
        requestDataFromInternet(context, BASE_URL_NOW_PLAYING_MOVIES, API_KEY);
    }

    /**
     * Using Fast Android Networking library to fetch data from internet , data is transferred to activity using an interface
     *
     * @param baseURL from where data is to be downloaded
     * @param API_KEY to access the database
     */
    private static void requestDataFromInternet(final Context context, String baseURL, String API_KEY) {
        //To transfer data back to the context activity
        final JsonDataDownloadInterface requestListener = (JsonDataDownloadInterface) context;
        AndroidNetworking.get(baseURL)
                .addQueryParameter(QUERY_PARAM_API_KEY, API_KEY)
                .addQueryParameter(QUERY_PARAM_LANGUAGE, PARAM_LANGUAGE)
                .addQueryParameter(QUERY_PARAM_INCLUDE_ADULT, "false")
                .addQueryParameter(QUERY_PARAM_PAGE, "1")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //If data is downloaded successfully onResponse is called in the context activity
                        requestListener.onResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        //If there is some error onError is called in the context activity
                        requestListener.onError(anError.toString());
                    }
                });
    }

}
