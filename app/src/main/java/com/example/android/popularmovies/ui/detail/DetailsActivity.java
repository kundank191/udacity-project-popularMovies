package com.example.android.popularmovies.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.popularmovies.AppExecutors;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.GlideApp;
import com.example.android.popularmovies.Utils.JSONUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.data.database.AppDatabase;
import com.example.android.popularmovies.data.network.MovieCreditsResponse;
import com.example.android.popularmovies.data.network.MovieResponse;
import com.example.android.popularmovies.data.network.MovieReviewsResponse;
import com.example.android.popularmovies.data.network.MovieTrailersResponse;
import com.example.android.popularmovies.ui.Interfaces.JsonDataDownloadInterface;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements JsonDataDownloadInterface {

    public static final String MOVIE_OBJECT_INTENT_KEY = "10123";
    public static final String MOVIE_ID_INTENT_KEY = "10122";
    private String API_KEY;
    private MovieDetailViewModelFactory mFactory;
    private MovieDetailViewModel mViewModel;
    private AppDatabase mDb;
    @BindView(R.id.custom_back_button)
    ImageView mBackButton;
    @BindView(R.id.backdrop_iv_detail_activity)
    ImageView mBackdropIV;
    @BindView(R.id.moviePoster_iv)
    ImageView moviePosterIV;
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
    @BindView(R.id.rv_similar_movies)
    RecyclerView mSimilarMovieRV;
    @BindView(R.id.rv_trailers)
    RecyclerView mTrailerRV;
    @BindView(R.id.trailer_button)
    ImageView mTrailerButton;
    @BindView(R.id.like_button)
    LikeButton mLikeButton;
    @BindView(R.id.cast_group)
    Group mCastGroup;
    @BindView(R.id.review_group)
    Group mReviewGroup;
    @BindView(R.id.trailer_group)
    Group mTrailerGroup;
    @BindView(R.id.similar_movies_view_group)
    CardView mSimilarMovieGroup;
    SimilarMovieAdapter mSimilarMovieAdapter;
    TrailerAdapter mTrailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Getting the API KEY
        API_KEY = getResources().getString(R.string.API_KEY);
        //Initialize
        init();
        //Check if viewModel Has data
        if (mViewModel.getMovie() != null) {
            setDataFromViewModel();
            initLikeFunction();
        } else {
            setDataFromIntent(getIntent());
        }
    }

    /**
     * Will run in on create and initialize all the views
     */
    private void init() {
        //initializing database
        mDb = AppDatabase.getInstance(getApplicationContext());

        ButterKnife.bind(this);
        mFactory = new MovieDetailViewModelFactory();
        mViewModel = ViewModelProviders.of(this, mFactory).get(MovieDetailViewModel.class);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * Implement what happens when like button is pressed
     */
    private void initLikeFunction() {
        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    final MovieResponse movie = mViewModel.getMovie();

                    @Override
                    public void run() {
                        mDb.movieDao().addFavMovie(movie);
                        Log.i("Added", "Movie added man" + movie.getMovieID());
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    final MovieResponse movie = mViewModel.getMovie();

                    @Override
                    public void run() {
                        mDb.movieDao().removeFavMovie(movie);
                        Log.i("Removed", "Movie removed man" + movie.getMovieID());
                    }
                });
            }
        });
    }

    /**
     * This Methods checks for which data is available in the view model and populates it if data
     * is absent then it makes a network request
     */
    private void setDataFromViewModel() {
        populateUI(mViewModel.getMovie());
        String movieID = mViewModel.getMovie().getMovieID();

        //If viewModel has Cast list it will be shown else downloaded
        if (mViewModel.getCastList() != null) {
            populateCast(mViewModel.getCastList());
        } else {
            NetworkUtils.getMovieCredits(this, movieID, API_KEY);
        }

        //If viewModel has Review list it will be shown else downloaded
        if (mViewModel.getReviewsList() != null) {
            populateReviews(mViewModel.getReviewsList());
        } else {
            NetworkUtils.getMovieReviews(this, movieID, API_KEY);
        }

        //If viewModel has Similar list it will be shown else downloaded
        if (mViewModel.getSimilarMovieList() != null) {
            populateSimilarMovies(mViewModel.getSimilarMovieList());
        } else {
            NetworkUtils.getSimilarMovies(this, movieID, API_KEY);
        }

        //If viewModel has Trailer list it will be shown else downloaded
        if (mViewModel.getTrailersList() != null) {
            populateTrailers(mViewModel.getTrailersList());
        } else {
            NetworkUtils.getMovieVideos(this, movieID, API_KEY);
        }
    }

    /***
     * If data is not available in the ViewModel then data will either be fetched from the intent or from the net
     * depending on the passed intent
     */
    private void setDataFromIntent(Intent intent) {
        //Get the intent and then retrieve the movie object from it
        if (intent != null) {
            if (intent.hasExtra(MOVIE_ID_INTENT_KEY)) {
                //If only movie ID is passed then movie details will first be downloaded from the internet and then displayed
                String movieID = intent.getStringExtra(MOVIE_ID_INTENT_KEY);
                NetworkUtils.getMovieDetails(this, movieID, API_KEY);
                getMovieExtrasFromInternet(movieID);

            } else if (intent.hasExtra(MOVIE_OBJECT_INTENT_KEY)) {
                //If intent passed contains some cached data about the movie then that will be extracted and used
                MovieResponse movieResponse = (MovieResponse) intent.getSerializableExtra(MOVIE_OBJECT_INTENT_KEY);
                if (movieResponse != null) {
                    mViewModel.setMovie(movieResponse);
                    populateUI(mViewModel.getMovie());
                    //Manipulate here after getting movie data
                    getMovieExtrasFromInternet(movieResponse.getMovieID());

                } else {
                    //if object sent is null
                    closeOnError();
                }
            } else {
                //If intent doesn't have any relevant intent key
                closeOnError();
            }
        } else {
            closeOnError();
        }
    }

    /**
     * This functions makes a network request to get Movie Reviews, Credits, Videos, Similar Movies
     *
     * @param movieID the id of the movie whose data is to be downloaded
     */
    private void getMovieExtrasFromInternet(String movieID) {
        //get Movie Details , Reviews , Credits , Videos and Similar movies
        NetworkUtils.getMovieReviews(this, movieID, API_KEY);
        NetworkUtils.getMovieCredits(this, movieID, API_KEY);
        NetworkUtils.getMovieVideos(this, movieID, API_KEY);
        NetworkUtils.getSimilarMovies(this, movieID, API_KEY);
    }


    /**
     * This function populates the UI with data
     *
     * @param movieResponse from this object all the data will be taken out to populate views
     */
    private void populateUI(MovieResponse movieResponse) {
        //enabling functionality to favourite the movie on button click
        initLikeFunction();
        //Check if movie is a favourite
        setIfFavourite(movieResponse.getMovieID());
        //Setting Image backdrop
        GlideApp.with(this)
                .load(NetworkUtils.getBackdropImageURL(movieResponse.getBackdropPath()))
                .placeholder(R.drawable.image_place_holder_back_drop)
                .error(R.drawable.broken_image_back_drop)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mBackdropIV);
        mBackdropIV.setContentDescription(movieResponse.getMovieTitle());
        //Setting the poster image
        GlideApp.with(this)
                .load(NetworkUtils.getPosterImageURL(movieResponse.getPosterPath()))
                .placeholder(R.drawable.image_place_holder_poster)
                .error(R.drawable.broken_image_poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(moviePosterIV);
        moviePosterIV.setContentDescription(movieResponse.getMovieTitle());
        //This will populate the ratings text view \u2b50 is a code for a star
        String ratingText = (movieResponse.getVoteAverage() / 2) + " \u2b50 " + movieResponse.getVoteCount() + "  " + getResources().getString(R.string.ratings);
        ratingTV.setText(ratingText);
        movieNameTV.setText(movieResponse.getMovieTitle());
        movieReleaseDateTV.setText(movieResponse.getReleaseDate());
        movieSynopsisTV.setText(movieResponse.getMovieSynopsis());

    }

    /**
     * Takes in movie Id and checks if it in the favourite movie list and updates UI accordingly
     *
     * @param movieID the ID of the movie
     */
    private void setIfFavourite(final String movieID) {
        final MovieResponse[] movieResponse = new MovieResponse[1];
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieResponse[0] = mDb.movieDao().checkIfExist(movieID);
                //If the movie belongs to user favourites then it will be shown as liked
                if (movieResponse[0] != null)
                    mLikeButton.setLiked(true);
                else
                    mLikeButton.setLiked(false);
            }
        });
    }

    /**
     * @param listTrailers list of trailers which will be shown in the trailers recycler view
     */
    private void populateTrailers(final List<MovieTrailersResponse> listTrailers) {
        if (listTrailers != null && listTrailers.size() != 0) {
            //making trailer visible button visible when trailer is avaliable
            mTrailerButton.setVisibility(View.VISIBLE);
            mTrailerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Video_Id = listTrailers.get(0).getTrailerVideoID();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + Video_Id)));
                }
            });
            mTrailerAdapter = new TrailerAdapter(this, listTrailers);
            mTrailerRV.setAdapter(mTrailerAdapter);
            mTrailerRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            mTrailerGroup.setVisibility(View.GONE);
        }
    }

    /**
     * @param listReviews list of reviews which will be displayed in the review section
     *                    A maximum of only three reviews will be shown on the details page
     */
    private void populateReviews(List<MovieReviewsResponse> listReviews) {
        if (listReviews != null && listReviews.size() != 0) {
            int maxReviews = getResources().getInteger(R.integer.num_reviews_max);
            List<MovieReviewsResponse> list;
            if (listReviews.size() >= 3) {
                list = listReviews.subList(0, maxReviews);
            } else {
                list = listReviews;
            }
            mReviewAdapter = new ReviewAdapter(this, list);
            mReviewRV.setAdapter(mReviewAdapter);
            mReviewRV.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mReviewGroup.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @param listCast list of cast which will be used to populate the cast recycler view
     */
    private void populateCast(List<MovieCreditsResponse> listCast) {
        if (listCast != null && listCast.size() != 0) {
            mCastAdapter = new CastAdapter(this, listCast);
            mCastRV.setAdapter(mCastAdapter);
            mCastRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            mCastRV.setHorizontalFadingEdgeEnabled(true);
        } else {
            mCastGroup.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @param listSimilarMovie list of similar movies which will be suggested to the user
     */
    private void populateSimilarMovies(List<MovieResponse> listSimilarMovie) {
        if (listSimilarMovie != null && listSimilarMovie.size() != 0) {
            mSimilarMovieAdapter = new SimilarMovieAdapter(this, listSimilarMovie);
            mSimilarMovieRV.setAdapter(mSimilarMovieAdapter);
            mSimilarMovieRV.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
            mCastRV.setHorizontalFadingEdgeEnabled(true);
        } else {
            mSimilarMovieGroup.setVisibility(View.GONE);
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
        Log.i(getLocalClassName(), error);
    }

    /**
     * This method is triggered by the networkUtils class after receiving data
     * This method gets specific data defined by params like similar movies and movie trailers
     *
     * @param response the response by the internet
     */
    @Override
    public void onResponse(JSONObject response, String param) {
        Log.i("JSON RESPONSE : " + param, response.toString());
        switch (param) {
            case NetworkUtils.PATH_PARAM_CREDITS:
                mViewModel.setCastList(JSONUtils.getMovieCast(response));
                populateCast(mViewModel.getCastList());
                break;
            case NetworkUtils.PATH_PARAM_REVIEWS:
                mViewModel.setReviewsList(JSONUtils.getMovieReviews(response));
                populateReviews(mViewModel.getReviewsList());
                break;
            case NetworkUtils.PATH_PARAM_SIMILAR:
                mViewModel.setSimilarMovieList(JSONUtils.getMovieList(response));
                populateSimilarMovies(mViewModel.getSimilarMovieList());
                break;
            case NetworkUtils.PATH_PARAM_VIDEOS:
                mViewModel.setTrailersList(JSONUtils.getMovieTrailers(response));
                populateTrailers(mViewModel.getTrailersList());
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
        MovieResponse movie = JSONUtils.getMovieObject(response);
        mViewModel.setMovie(movie);
        populateUI(mViewModel.getMovie());
    }

}
