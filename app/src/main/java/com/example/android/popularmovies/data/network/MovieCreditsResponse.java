package com.example.android.popularmovies.data.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kundan on 30-07-2018.
 */
public class MovieCreditsResponse {

    @SerializedName("name")
    private
    String personName;

    @SerializedName("profile_path")
    private
    String personImageUrlPath;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonImageUrlPath() {
        return personImageUrlPath;
    }

    public void setPersonImageUrlPath(String personImageUrlPath) {
        this.personImageUrlPath = personImageUrlPath;
    }


}
