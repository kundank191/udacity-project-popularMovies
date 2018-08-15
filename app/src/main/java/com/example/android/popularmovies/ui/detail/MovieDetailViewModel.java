package com.example.android.popularmovies.ui.detail;

import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.data.network.MovieCreditsResponse;
import com.example.android.popularmovies.data.network.MovieResponse;
import com.example.android.popularmovies.data.network.MovieReviewsResponse;
import com.example.android.popularmovies.data.network.MovieTrailersResponse;

import java.util.List;

/**
 * Created by Kundan on 15-08-2018.
 */
public class MovieDetailViewModel extends ViewModel {

    public MovieDetailViewModel(){

    }

    private MovieResponse mMovie = null;
    private List<MovieTrailersResponse> mTrailersList = null;
    private List<MovieResponse> mSimilarMovieList = null;
    private List<MovieCreditsResponse> mCastList = null;
    private List<MovieReviewsResponse> mReviewsList = null;

    public MovieResponse getMovie() {
        return mMovie;
    }

    public void setMovie(MovieResponse mMovie) {
        this.mMovie = mMovie;
    }

    public List<MovieTrailersResponse> getTrailersList() {
        return mTrailersList;
    }

    public void setTrailersList(List<MovieTrailersResponse> mTrailersList) {
        this.mTrailersList = mTrailersList;
    }

    public List<MovieResponse> getSimilarMovieList() {
        return mSimilarMovieList;
    }

    public void setSimilarMovieList(List<MovieResponse> mSimilarMovieList) {
        this.mSimilarMovieList = mSimilarMovieList;
    }

    public List<MovieCreditsResponse> getCastList() {
        return mCastList;
    }

    public void setCastList(List<MovieCreditsResponse> mCastList) {
        this.mCastList = mCastList;
    }

    public List<MovieReviewsResponse> getReviewsList() {
        return mReviewsList;
    }

    public void setReviewsList(List<MovieReviewsResponse> mReviewsList) {
        this.mReviewsList = mReviewsList;
    }
}
