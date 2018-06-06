package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private final String MOVIE_OBJECT = "10123";
    @BindView(R.id.app_bar_detail_activity)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.movieBackdrop_iv)
    ImageView movieBackdrop_iv;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            Movie movie = (Movie) intent.getSerializableExtra(MOVIE_OBJECT);
            if (movie != null) {
                populateUI(movie);
            } else {
                closeOnError();
            }
        } else {
            closeOnError();
        }
    }

    private void populateUI(Movie movie) {
        Picasso.with(this)
                .load(NetworkUtils.getBackdropImageURL(movie.getBackdropPath()))
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.ic_photo_broken)
                .into(movieBackdrop_iv);
        ;
        Picasso.with(this)
                .load(NetworkUtils.getPosterImageURL(movie.getPosterPath()))
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.ic_photo_broken)
                .into(moviePoster_iv);

        String ratingText = (movie.getVoteAverage()/2) + " \u25CF " + movie.getVoteCount() + " " + getResources().getString(R.string.ratings);
        ratingTV.setText(ratingText);
        movieNameTV.setText(movie.getMovieTitle());
        movieReleaseDateTV.setText(movie.getReleaseDate());
        movieSynopsisTV.setText(movie.getMovieSynopsis());

    }

    private void closeOnError() {
        Toast.makeText(this, R.string.detail_activity_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
