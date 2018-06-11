package com.example.android.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private final String MOVIE_OBJECT = "10123";
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
                .placeholder(R.drawable.image_place_holder_back_drop)
                .error(R.drawable.broken_image_back_drop)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mToolbar.setBackground(new BitmapDrawable(getBaseContext().getResources(),bitmap));
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
        ;
        Picasso.with(this)
                .load(NetworkUtils.getPosterImageURL(movie.getPosterPath()))
                .placeholder(R.drawable.image_place_holder_poster)
                .error(R.drawable.broken_image_poster)
                .into(moviePoster_iv);

        String ratingText = (movie.getVoteAverage()/2) + " \u2b50 " + movie.getVoteCount() + " " + getResources().getString(R.string.ratings);
        ratingTV.setText(ratingText);
        movieNameTV.setText(movie.getMovieTitle());
        movieReleaseDateTV.setText(movie.getReleaseDate());
        movieSynopsisTV.setText(movie.getMovieSynopsis());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void closeOnError() {
        Toast.makeText(this, R.string.detail_activity_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
