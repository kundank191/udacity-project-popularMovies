package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.Utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO ADD YOUR API KEY IN String.xml FILE (GET IT FROM HERE : https://developers.themoviedb.org/3/getting-started )
        String response = NetworkUtils.getPopularMovieList(getResources().getString(R.string.API_KEY));
        Log.i("12345",response);
    }
}
