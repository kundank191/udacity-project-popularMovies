package com.example.android.popularmovies.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.example.android.popularmovies.data.network.MovieResponse;
import com.example.android.popularmovies.ui.Interfaces.ListItemClickInterface;
import com.example.android.popularmovies.ui.detail.DetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 04-06-2018.
 * RecyclerView adapter for movie objects to be displayed in the MainActivity
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //This value will be used in intent
    private final String MOVIE_OBJECT = DetailsActivity.MOVIE_OBJECT_INTENT_KEY;

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

    private List<MovieResponse> mMovieResponseList;
    private final Context mContext;

    /**
     * @param context   of the Activity
     * @param movieResponseList The list of movie to be displayed
     */
    public MovieAdapter(Context context, List<MovieResponse> movieResponseList) {
        mContext = context;
        mMovieResponseList = movieResponseList;
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
        //Getting a MovieResponse object with respect to position
        final MovieResponse movieResponse = mMovieResponseList.get(position);

        //Populating views
        holder.movieNameTV.setText(movieResponse.getMovieTitle());
        //The content description of the imageView will be the title of the movieResponse
        holder.moviePosterIV.setContentDescription(movieResponse.getMovieTitle());

        GlideApp.with(mContext)
                .load(NetworkUtils.getPosterImageURL(movieResponse.getPosterPath()))
                .placeholder(R.drawable.image_place_holder_poster)
                .error(R.drawable.broken_image_poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.moviePosterIV);

        //When an Item is clicked then it will open detail activity with passed data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItemClickInterface onClickInterface = (ListItemClickInterface) mContext;
                onClickInterface.onListItemClicked(MOVIE_OBJECT, movieResponse,holder.moviePosterIV);
            }
        });
    }

    public void setMovieList(List<MovieResponse> mMovieResponseList) {
        this.mMovieResponseList = mMovieResponseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovieResponseList.size();
    }
}
