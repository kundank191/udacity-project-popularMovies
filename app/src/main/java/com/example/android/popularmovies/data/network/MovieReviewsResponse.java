package com.example.android.popularmovies.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kundan on 30-07-2018.
 * POJO object to store the review of movies
 */
public class MovieReviewsResponse {
    @SerializedName("author")
    private String authorName;
    @SerializedName("content")
    private String authorReview;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorReview() {
        return authorReview;
    }

    public void setAuthorReview(String authorReview) {
        this.authorReview = authorReview;
    }

}
