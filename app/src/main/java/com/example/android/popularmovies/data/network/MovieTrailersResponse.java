package com.example.android.popularmovies.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kundan on 30-07-2018.
 * POJO for storing movie trailer response
 */
public class MovieTrailersResponse {
    @SerializedName("key")
    private String trailerVideoID;
    @SerializedName("name")
    private String trailerName;

    public String getTrailerVideoID() {
        return trailerVideoID;
    }

    public void setTrailerVideoID(String trailerVideoID) {
        this.trailerVideoID = trailerVideoID;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

}
