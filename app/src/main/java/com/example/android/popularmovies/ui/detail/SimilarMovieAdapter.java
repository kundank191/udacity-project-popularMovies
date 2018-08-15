package com.example.android.popularmovies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.GlideApp;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.data.MovieResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 02-08-2018.
 */
public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieTitle_tv)
        TextView movieNameTV;
        @BindView(R.id.moviePoster_iv)
        ImageView moviePosterIV;
        @BindView(R.id.rating_tv)
        TextView movieRatingsTV;
        @BindView(R.id.similar_movie_item)
        ConstraintLayout movieItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<MovieResponse> mMovieList;
    private final Context mContext;

    /**
     * @param context   of the Activity
     * @param movieList The list of movie to be displayed
     */
    SimilarMovieAdapter(Context context, List<MovieResponse> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View movieView = layoutInflater.inflate(R.layout.list_similar_movies_row_item, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieResponse movie = mMovieList.get(position);

        holder.movieNameTV.setText(movie.getMovieTitle());
        holder.moviePosterIV.setContentDescription(movie.getMovieTitle());
        holder.movieRatingsTV.setText(String.format("%s ⭐ ", movie.getVoteAverageOutOfFive().toString()));
        //on click a details activity will open for that movie
        holder.movieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE_ID_INTENT_KEY, movie.getMovieID());
                mContext.startActivity(intent);
            }
        });
        GlideApp.with(mContext)
                .load(NetworkUtils.getPosterImageURL(movie.getPosterPath()))
                .placeholder(R.drawable.image_place_holder_poster)
                .error(R.drawable.broken_image_poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.moviePosterIV);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
