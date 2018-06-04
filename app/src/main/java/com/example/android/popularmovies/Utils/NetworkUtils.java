package com.example.android.popularmovies.Utils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

/**
 * Created by Kundan on 03-06-2018.
 */
public class NetworkUtils {

    private static final String BASE_URL_POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?"
                                ,BASE_URL_NOW_PLAYING_MOVIES = "https://api.themoviedb.org/3/movie/now_playing?"
                                ,BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w185/"
                                ,QUERY_PARAM_API_KEY = "api_key"
                                ,QUERY_PARAM_LANGUAGE = "language"
                                ,QUERY_PARAM_SORT = "sort_by"
                                ,QUERY_PARAM_INCLUDE_ADULT = "include_adult"
                                ,QUERY_PARAM_PAGE = "page"
                                ,PARAM_LANGUAGE = "en-US";

    /**
     *
     * @param imagePath Takes the path of the image in the database
     * @return the complete URI of the image path
     */
    public static String getImageURL(String imagePath){
        return BASE_URL_IMAGE + imagePath;
    }

    /**
     * @param API_KEY The api key to access the online database
     * @return a list of Popular movies
     */
    public static String getPopularMovieList(String API_KEY){
        return getDataFromInternet(BASE_URL_POPULAR_MOVIES,API_KEY);
    }

    /**
     * @param API_KEY The api key to access the online database
     * @return a list of now playing movies
     */
    public static String getNowPlayingMovies(String API_KEY){
        return getDataFromInternet(BASE_URL_NOW_PLAYING_MOVIES,API_KEY);
    }

    /**
     *
     * @param baseURL from where data is to be downloaded
     * @param API_KEY to access the database
     * @return the response
     */
    private static String getDataFromInternet(String baseURL, String API_KEY){
        final String[] responseString = {""};
        AndroidNetworking.get(baseURL)
                .addQueryParameter(QUERY_PARAM_API_KEY,API_KEY)
                .addQueryParameter(QUERY_PARAM_LANGUAGE,PARAM_LANGUAGE)
                .addQueryParameter(QUERY_PARAM_INCLUDE_ADULT,"false")
                .addQueryParameter(QUERY_PARAM_PAGE,"1")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseString[0] = response.toString();
                    }

                    @Override
                    public void onError(ANError anError) {
                        responseString[0] = null;
                    }
                });
        return responseString[0];
    }
}
