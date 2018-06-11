package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Interfaces.ListItemClickInterface;
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

    //This value will be used in intent
    private final String MOVIE_OBJECT = "10123";

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
    public void onBindViewHolder(@NonNull final MovieAdapter.ViewHolder holder, int position) {
        //Getting a Movie object with respect to position
        final Movie movie = mMovieList.get(position);

        //Populating views
        holder.movieNameTV.setText(movie.getMovieTitle());
        //The content description of the imageView will be the title of the movie
        holder.moviePosterIV.setContentDescription(movie.getMovieTitle());
        Picasso.with(mContext)
                .load(NetworkUtils.getPosterImageURL(movie.getPosterPath()))
                .placeholder(R.drawable.image_place_holder_poster)
                .error(R.drawable.broken_image_poster)
                .into(holder.moviePosterIV);

        //When an Item is clicked then it will open detail activity with passed data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItemClickInterface onClickInterface = (ListItemClickInterface) mContext;
                onClickInterface.onListItemClicked(MOVIE_OBJECT,movie,holder.moviePosterIV);
            }
        });
    }

    public void setMovieList(List<Movie> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
