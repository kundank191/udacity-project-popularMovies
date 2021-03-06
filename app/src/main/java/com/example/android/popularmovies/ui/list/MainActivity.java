package com.example.android.popularmovies.ui.list;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.JSONUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.data.database.AppDatabase;
import com.example.android.popularmovies.data.MovieResponse;
import com.example.android.popularmovies.ui.Interfaces.JsonDataDownloadInterface;
import com.example.android.popularmovies.ui.Interfaces.ListItemClickInterface;
import com.example.android.popularmovies.ui.detail.DetailsActivity;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements JsonDataDownloadInterface, ListItemClickInterface {

    private String API_KEY = BuildConfig.Api_Key;
    final private int CODE_POPULAR_MOVIE = 123;
    final private int CODE_TOP_RATED_MOVIES = 126;
    final private int CODE_FAV_MOVIES = 129;
    private int NUM_COLUMNS_GRID_LAYOUT = 2;
    private MovieViewModel viewModel;
    private AppDatabase mDb;
    //Binding views
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolBar_main_activity)
    Toolbar toolbar;
    MovieAdapter mAdapter;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_state_view)
    TextView mEmptyStateView;
    @BindView(R.id.no_internet_view)
    TextView mNoInternetView;
    @BindView(R.id.no_favourite_view)
    TextView mNoFavouritesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //Set num of columns according to screen orientation
        NUM_COLUMNS_GRID_LAYOUT = getResources().getInteger(R.integer.num_colums_grid_view);

        //Getting database instance
        mDb = AppDatabase.getInstance(getApplicationContext());

        //Initializing view model Factory
        MovieViewModelFactory mFactory = new MovieViewModelFactory();
        viewModel = ViewModelProviders.of(this, mFactory).get(MovieViewModel.class);

        //If View model has a movie list then it will be displayed else new data will be downloaded
        if (viewModel.getMovieList() != null) {
            populateData(viewModel.getMovieList());
        } else {
            //Initially when app is loaded it will show popular movies
            downloadAndPopulateMovieData(CODE_POPULAR_MOVIE);
        }
    }

    /**
     * Takes in a preference code and then calls populateData(param) to display movie list in the recycler view
     *
     * @param movieCode the preference according to which movies are to be displayed
     */
    private void downloadAndPopulateMovieData(int movieCode) {
        //If favourite movies has been requested
        if (movieCode == CODE_FAV_MOVIES) {
            //Favourite movies will be queried from the database
            viewModel.getFavouriteMovieList(getApplication()).observe(this, new Observer<List<MovieResponse>>() {
                @Override
                public void onChanged(@Nullable List<MovieResponse> favMovies) {
                    viewModel.setMovieList(favMovies);
                    if (toolbar.getTitle() == getResources().getString(R.string.favourites)) {
                        if (favMovies != null && favMovies.size() != 0) {
                            populateData(viewModel.getMovieList());
                        } else {
                            showNoFavouritesUI();
                        }
                    }
                }
            });
        }
        //Checks if device is connected to the internet
        if (isNetworkConnected()) {
            //A progress bar will be shown before download begins
            showLoadingScreen();
            //get data according to the preference code
            switch (movieCode) {
                case CODE_POPULAR_MOVIE:
                    NetworkUtils.getPopularMovieList(this, API_KEY);
                    break;
                case CODE_TOP_RATED_MOVIES:
                    NetworkUtils.getTopRatedMovieList(this, API_KEY);
                    break;
            }
        } else {
            showNoInternetUI();
        }
    }

    /**
     * This method is triggered by the networkUtils class , when data is downloaded from the internet successfully
     *
     * @param response the response from the internet
     */
    @Override
    public void onResponse(JSONObject response) {
        //Updating view model with new data and using this data to populate views
        viewModel.setMovieList(JSONUtils.getMovieList(response));
        populateData(viewModel.getMovieList());
    }

    /**
     * This method is triggered by the networkUtils class , when there was an error getting data
     *
     * @param error the error which occurred while downloading data
     */
    @Override
    public void onError(String error) {
        Log.e("MainActivity", error);
        showErrorUI();
    }

    /**
     * This method is triggered by the networkUtils class after receiving data
     * This method gets specific data defined by params like similar movies and movie trailers
     *
     * @param response the response by the internet
     */
    @Override
    public void onResponse(JSONObject response, String param) {

    }

    /**
     * Populates the recycler view with movie list
     * if movie list is null it will show an error screen
     *
     * @param movieResponseList a list of movie objects
     */
    private void populateData(List<MovieResponse> movieResponseList) {
        if (movieResponseList == null) {
            showErrorUI();
        } else {
            mAdapter = new MovieAdapter(this, movieResponseList);
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
     * Handles what happens when a recycle view's item is clicked
     *
     * @param key           this will be used to extract the movieResponse object in the details activity from intent extras
     * @param movieResponse object to be passed
     */
    @Override
    public void onListItemClicked(String key, MovieResponse movieResponse, View sharedView) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(key, movieResponse);
        //Exiting main activity with a transition
        Bundle bundle = ActivityOptions
                .makeSceneTransitionAnimation(this
                        , sharedView
                        , sharedView.getTransitionName())
                .toBundle();
        this.startActivity(intent, bundle);
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
                toolbar.setTitle(R.string.popular_movies);
                //populates the recycler view with updated popular movie list
                downloadAndPopulateMovieData(CODE_POPULAR_MOVIE);
                break;
            case R.id.menu_top_rated_movies:
                toolbar.setTitle(R.string.top_rated_movies);
                //populates the recycler view with updated now playing movie list
                downloadAndPopulateMovieData(CODE_TOP_RATED_MOVIES);
                break;
            case R.id.menu_fav_movies:
                toolbar.setTitle(R.string.favourites);
                //Populates the recycler view with favourite movies
                downloadAndPopulateMovieData(CODE_FAV_MOVIES);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * It checks if the device is connected to the internet or not
     *
     * @return a boolean value indicating the state of network
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * If an error occurs then an error layout will be shown
     */
    private void showErrorUI() {
        mNoFavouritesView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setVisibility(View.VISIBLE);
        mNoInternetView.setVisibility(View.GONE);
    }

    /**
     * When data is being loaded then a loading layout will be shown
     */
    private void showLoadingScreen() {
        mNoFavouritesView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyStateView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.GONE);
    }

    /**
     * If there is no internet connection then a No internet layout will be shown
     */
    private void showNoInternetUI() {
        mNoFavouritesView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.VISIBLE);
    }

    /**
     * If there is no internet connection then a No internet layout will be shown
     */
    private void showNoFavouritesUI() {
        mNoFavouritesView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mEmptyStateView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.GONE);
    }
}
