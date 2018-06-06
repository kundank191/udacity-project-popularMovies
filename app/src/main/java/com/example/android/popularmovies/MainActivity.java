package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.android.popularmovies.Adapters.MovieAdapter;
import com.example.android.popularmovies.Utils.JSONUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.models.Movie;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private String API_KEY;
    final private int CODE_POPULAR_MOVIE = 123;
    final private int CODE_NOW_PLAYING_MOVIE = 124;
    private int NUM_COLUMNS_GRID_LAYOUT = 2;
    //Binding views
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolBar_main_activity)
    Toolbar toolbar;
    MovieAdapter mAdapter;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_state_view)
    LinearLayout mEmptyStateView;
    @BindView(R.id.no_internet_view)
    LinearLayout mNoInternetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO ADD YOUR API KEY IN String.xml FILE (GET IT FROM HERE : https://developers.themoviedb.org/3/getting-started )
        API_KEY = getResources().getString(R.string.API_KEY);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //Initially when app is loaded it will show popular movies
        downloadAndPopulateMovieData(CODE_POPULAR_MOVIE);
    }

    /**
     * Takes in a preference code and then calls populateData(param) to display movie list in the recycler view
     * @param movieCode the preference according to which movies are to be displayed
     */
    private void downloadAndPopulateMovieData(int movieCode) {
        //Checks if device is connected to the internet
        if (isNetworkConnected()) {
            //A progress bar will be shown before download begins
            showLoadingScreen();
            ANRequest.GetRequestBuilder requestBuilder;
            //Build request builder according to the preference code
            switch (movieCode) {
                case CODE_POPULAR_MOVIE:
                    requestBuilder = NetworkUtils.requestPopularMovieList(API_KEY);
                    break;
                case CODE_NOW_PLAYING_MOVIE:
                    requestBuilder = NetworkUtils.requestNowPlayingMovieList(API_KEY);
                    break;
                default:
                    return;
            }
            requestBuilder.build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            populateData(JSONUtils.getMovieList(response));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("MainActivity", anError.toString());
                            showErrorUI();
                        }
                    });
        } else {
            showNoInternetUI();
        }
    }

    /**
     * Populates the recycler view with movie list
     * if movie list is null it will show an error screen
     * @param movieList a list of movie objects
     */
    private void populateData(List<Movie> movieList) {
        if (movieList == null) {
            showErrorUI();
        } else {
            mAdapter = new MovieAdapter(this, movieList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS_GRID_LAYOUT));
            //Show and hide relevant views
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoInternetView.setVisibility(View.GONE);
        }
    }

    /**
     * Inflating the menu file
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Adding actions to the menu items
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular_movies:
                downloadAndPopulateMovieData(CODE_POPULAR_MOVIE);
                toolbar.setTitle(R.string.popular_movies);
                break;
            case R.id.menu_now_playing_movies:
                downloadAndPopulateMovieData(CODE_NOW_PLAYING_MOVIE);
                toolbar.setTitle(R.string.now_playing_movies);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * It checks if the device is connected to the internet or not
     * @return a boolean value indicating the state of network
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * If an error occurs then an error layout will be shown
     */
    private void showErrorUI() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setVisibility(View.VISIBLE);
        mNoInternetView.setVisibility(View.GONE);
    }

    /**
     * When data is being loaded then a loading layout will be shown
     */
    private void showLoadingScreen() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyStateView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.GONE);
    }

    /**
     * If there is no internet connection then a No internet layout will be shown
     */
    private void showNoInternetUI() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.VISIBLE);
    }
}
