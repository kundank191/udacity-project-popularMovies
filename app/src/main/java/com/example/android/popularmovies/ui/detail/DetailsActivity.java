package com.example.android.popularmovies.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.GlideApp;
import com.example.android.popularmovies.Utils.JSONUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.data.network.MovieCreditsResponse;
import com.example.android.popularmovies.data.network.MovieResponse;
import com.example.android.popularmovies.data.network.MovieReviewsResponse;
import com.example.android.popularmovies.data.network.MovieTrailersResponse;
import com.example.android.popularmovies.ui.Interfaces.JsonDataDownloadInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements JsonDataDownloadInterface {

    public static final String MOVIE_OBJECT_INTENT_KEY = "10123";
    private String API_KEY;
    @BindView(R.id.app_bar_detail_activity)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.moviePoster_iv)
    ImageView moviePoster_iv;
    @BindView(R.id.movieTitle_tv)
    TextView movieNameTV;
    @BindView(R.id.movieReleaseDate_tv)
    TextView movieReleaseDateTV;
    @BindView(R.id.movieSynopsis_tv)
    TextView movieSynopsisTV;
    @BindView(R.id.rating_tv)
    TextView ratingTV;
    @BindView(R.id.rv_cast)
    RecyclerView mCastRV;
    CastAdapter mCastAdapter;
    @BindView(R.id.rv_reviews)
    RecyclerView mReviewRV;
    ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Getting the API KEY
        API_KEY = getResources().getString(R.string.API_KEY);
        //Binding views
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Get the intent and then retrieve the movie object from it
        Intent intent = getIntent();
        if (intent != null) {
            MovieResponse movieResponse = (MovieResponse) intent.getSerializableExtra(MOVIE_OBJECT_INTENT_KEY);
            if (movieResponse != null) {
                populateUI(movieResponse);
                //Manipulate here after getting movie data
                NetworkUtils.getMovieDetails(this, movieResponse.getMovieID(), API_KEY);
                NetworkUtils.getMovieReviews(this,movieResponse.getMovieID(),API_KEY);
                NetworkUtils.getMovieCredits(this,movieResponse.getMovieID(),API_KEY);
                NetworkUtils.getMovieVideos(this,movieResponse.getMovieID(),API_KEY);
                NetworkUtils.getSimilarMovies(this,movieResponse.getMovieID(),API_KEY);
            } else {
                closeOnError();
            }
        } else {
            closeOnError();
        }
    }

    /**
     * This function populates the UI with data
     *
     * @param movieResponse from this object all the data will be taken out to populate views
     */
    private void populateUI(MovieResponse movieResponse) {
        //Setting Image backdrop on toolbar
        //Picasso
        Picasso.with(this)
                .load(NetworkUtils.getBackdropImageURL(movieResponse.getBackdropPath()))
                .placeholder(R.drawable.image_place_holder_back_drop)
                .error(R.drawable.broken_image_back_drop)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mToolbar.setBackground(new BitmapDrawable(getBaseContext().getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        mToolbar.setBackground(errorDrawable);

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        mToolbar.setBackground(placeHolderDrawable);

                    }
                });
        //Setting the poster image
        GlideApp.with(this)
                .load(NetworkUtils.getPosterImageURL(movieResponse.getPosterPath()))
                .placeholder(R.drawable.image_place_holder_poster)
                .error(R.drawable.broken_image_poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(moviePoster_iv);

        //This will populate the ratings text view \u2b50 is a code for a star
        String ratingText = (movieResponse.getVoteAverage() / 2) + " \u2b50 " + movieResponse.getVoteCount() + "  " + getResources().getString(R.string.ratings);
        ratingTV.setText(ratingText);
        movieNameTV.setText(movieResponse.getMovieTitle());
        movieReleaseDateTV.setText(movieResponse.getReleaseDate());
        movieSynopsisTV.setText(movieResponse.getMovieSynopsis());

    }

    /**
     *
     * @param listTrailers list of trailers which will be shown in the trailers recycler view
     */
    private void populateTrailers(List<MovieTrailersResponse> listTrailers){
        if(listTrailers != null) {
            Log.i("Trailers", listTrailers.toString());
        }
    }

    /**
     *
     * @param listReviews list of reviews which will be displayed in the review section
     */
    private void populateReviews(List<MovieReviewsResponse> listReviews){
        if(listReviews != null) {
            mReviewAdapter = new ReviewAdapter(this,listReviews);
            mReviewRV.setAdapter(mReviewAdapter);
            mReviewRV.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    /**
     *
     * @param listCast list of cast which will be used to populate the cast recycler view
     */
    private void populateCast(List<MovieCreditsResponse> listCast){
        if(listCast != null) {
            mCastAdapter = new CastAdapter(this,listCast);
            mCastRV.setAdapter(mCastAdapter);
            mCastRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            mCastRV.setHorizontalFadingEdgeEnabled(true);
        }
    }

    /**
     *
     * @param listSimilarMovie list of similar movies which will be suggested to the user
     */
    private void populateSimilarMovies(List<MovieResponse> listSimilarMovie){
        if(listSimilarMovie != null) {
            Log.i("Similar Movies", listSimilarMovie.toString());
        }
    }

    /**
     * The home button has the same function as the back button , so that the shared element transition is reversed
     *
     * @param item menu item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    /**
     * If some error occurs this function will handle it
     */
    private void closeOnError() {
        Toast.makeText(this, R.string.detail_activity_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * This method is triggered by the networkUtils class , when there was an error getting data
     *
     * @param error the error which occurred while downloading data
     */
    @Override
    public void onError(String error) {

    }

    /**
     * This method is triggered by the networkUtils class after receiving data
     * This method gets specific data defined by params like similar movies and movie trailers
     *
     * @param response the response by the internet
     */
    @Override
    public void onResponse(JSONObject response, String param) {
        Log.i("JSON RESPONSE : " + param,response.toString());
        switch (param){
            case NetworkUtils.PATH_PARAM_CREDITS:
                populateCast(JSONUtils.getMovieCast(response));
                break;
            case NetworkUtils.PATH_PARAM_REVIEWS:
                populateReviews(JSONUtils.getMovieReviews(response));
                break;
            case NetworkUtils.PATH_PARAM_SIMILAR:
                break;
            case NetworkUtils.PATH_PARAM_VIDEOS:
                populateTrailers(JSONUtils.getMovieTrailers(response));
                break;
        }
    }

    /**
     * This method is triggered by the networkUtils class , when data is downloaded from the internet successfully
     *
     * @param response the response from the internet
     */
    @Override
    public void onResponse(JSONObject response) {

    }

}
