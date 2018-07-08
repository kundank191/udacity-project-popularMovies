package com.example.android.popularmovies.ui.Interfaces;

import org.json.JSONObject;

/**
 * Created by Kundan on 11-06-2018.
 * This interface is used to get data after downloading it from NetworkUtils class
 */
public interface JsonDataDownloadInterface {
    void onResponse(JSONObject response);

    void onError(String error);

    void onResponse(JSONObject response, String param);
}

