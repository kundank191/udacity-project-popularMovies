package com.example.android.popularmovies.Utils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;

/**
 * Created by Kundan on 03-06-2018.
 */
public class NetworkUtils {

    private static final String BASE_URL_POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?"
                                ,BASE_URL_NOW_PLAYING_MOVIES = "https://api.themoviedb.org/3/movie/now_playing?"
                                ,BASE_URL_IMAGE_POSTER = "http://image.tmdb.org/t/p/w500/"
                                ,BASE_URL_IMAGE_BACKDROP = "http://image.tmdb.org/t/p/w780/"
                                ,QUERY_PARAM_API_KEY = "api_key"
                                ,QUERY_PARAM_LANGUAGE = "language"
                                ,QUERY_PARAM_INCLUDE_ADULT = "include_adult"
                                ,QUERY_PARAM_PAGE = "page"
                                ,PARAM_LANGUAGE = "en-US";

    /**
     *
     * @param imagePath Takes the path of the image in the database
     * @return the complete URI of the image path
     */
    public static String getPosterImageURL(String imagePath){
        return BASE_URL_IMAGE_POSTER + imagePath;
    }

    /**
     *
     * @param imagePath Takes the path of the image in the database
     * @return the complete URI of the image path
     */
    public static String getBackdropImageURL(String imagePath){
        return BASE_URL_IMAGE_BACKDROP + imagePath;
    }

    /**
     * Create a requestBuilder for popular movies
     * @param API_KEY The api key to access the online database
     * @return a list of Popular movies
     */
    public static ANRequest.GetRequestBuilder requestPopularMovieList(String API_KEY){
        return requestDataFromInternet(BASE_URL_POPULAR_MOVIES,API_KEY);
    }

    /**
     * Create a requestBuilder for Now Playing movies
     * @param API_KEY The api key to access the online database
     * @return a list of Popular movies
     */
    public static ANRequest.GetRequestBuilder requestNowPlayingMovieList(String API_KEY){
        return requestDataFromInternet(BASE_URL_NOW_PLAYING_MOVIES,API_KEY);
    }

    /**
     * Using Fast Android Networking library to fetch data from internet
     * @param baseURL from where data is to be downloaded
     * @param API_KEY to access the database
     * @return An ANR request Object which can be built to download data
     */
    private static ANRequest.GetRequestBuilder requestDataFromInternet(String baseURL, String API_KEY){
        ANRequest.GetRequestBuilder object = AndroidNetworking.get(baseURL)
                .addQueryParameter(QUERY_PARAM_API_KEY,API_KEY)
                .addQueryParameter(QUERY_PARAM_LANGUAGE,PARAM_LANGUAGE)
                .addQueryParameter(QUERY_PARAM_INCLUDE_ADULT,"false")
                .addQueryParameter(QUERY_PARAM_PAGE,"1")
                .setPriority(Priority.LOW);
        return object;
    }

}
