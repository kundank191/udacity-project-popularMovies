package com.example.android.popularmovies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.network.MovieTrailersResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 15-08-2018.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.trailerPoster_iv)
        ImageView trailerPosterIV;
        @BindView(R.id.trailer_row_item)
        ConstraintLayout trailerItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private List<MovieTrailersResponse> mTrailerList;
    private Context mContext;

    /**
     * @param context   of the Activity
     * @param trailerList The list of trailer to be displayed
     */
    public TrailerAdapter(Context context, List<MovieTrailersResponse> trailerList) {
        mContext = context;
        mTrailerList = trailerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View movieView = layoutInflater.inflate(R.layout.list_trailer_row_item,parent,false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieTrailersResponse trailer = mTrailerList.get(position);
        holder.trailerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Video_Id = trailer.getTrailerVideoID();
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + Video_Id)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }
}
