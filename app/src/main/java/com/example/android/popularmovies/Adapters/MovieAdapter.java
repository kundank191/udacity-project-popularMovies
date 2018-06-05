package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 04-06-2018.
 * RecyclerView adapter for movie objects to be displayed in the MainActivity
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // View holder class for the RecyclerView row layout elements will be referenced here
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieName_tv)
        TextView movieNameTV;
        @BindView(R.id.moviePoster_iv)
        ImageView moviePosterIV;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Movie> mMovieList;
    private Context mContext;

    /**
     * @param context   of the Activity
     * @param movieList The list of movie to be displayed
     */
    public MovieAdapter(Context context, List<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    //Returns a ViewHolder
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View movieView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(movieView);
    }

    //Binds the data to the views
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        //Getting a Movie object with respect to position
        Movie movie = mMovieList.get(position);

        //Populating views
        holder.movieNameTV.setText(movie.getMovieName());
        Picasso.with(mContext)
                .load(NetworkUtils.getImageURL(movie.getPosterPath()))
                .placeholder(R.drawable.picture_placeholder)
                .error(R.drawable.ic_photo_broken)
                .into(holder.moviePosterIV);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}