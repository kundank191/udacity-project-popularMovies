package com.example.android.popularmovies.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.network.MovieReviewsResponse;

import java.util.List;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 01-08-2018.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.av_person)
        AvatarView personAV;
        @BindView(R.id.tv_person_name)
        TextView personNameTV;
        @BindView(R.id.tv_person_review)
        TextView personReviewTV;

        // View holder class for the RecyclerView row layout elements will be referenced here
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<MovieReviewsResponse> mReviewList;
    private final Context mContext;

    /**
     * @param context     of the Activity
     * @param listReviews The list of reviews to be displayed
     */
    ReviewAdapter(Context context, List<MovieReviewsResponse> listReviews) {
        mReviewList = listReviews;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.list_review_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieReviewsResponse reviewData = mReviewList.get(position);
        holder.personNameTV.setText(reviewData.getAuthorName());
        holder.personReviewTV.setText(reviewData.getAuthorReview());
        IImageLoader mImageLoader = new PicassoLoader();
        mImageLoader.loadImage(holder.personAV,reviewData.getAuthorName(),reviewData.getAuthorName());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}
