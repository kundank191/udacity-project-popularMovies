package com.example.android.popularmovies.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.data.network.MovieCreditsResponse;

import java.util.List;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kundan on 01-08-2018.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.av_person)
        AvatarView personAV;
        @BindView(R.id.tv_person_name)
        TextView personNameTV;

        // View holder class for the RecyclerView row layout elements will be referenced here
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<MovieCreditsResponse> mCastList;
    private final Context mContext;
    private IImageLoader mImageLoader;

    /**
     * @param context  of the Activity
     * @param castList The list of cast to be displayed
     */
    public CastAdapter(Context context, List<MovieCreditsResponse> castList) {
        mCastList = castList;
        mContext = context;
    }

    //Returns a ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View castView = layoutInflater.inflate(R.layout.list_cast_row_item, parent, false);
        return new ViewHolder(castView);
    }

    //Binds the data to the views
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieCreditsResponse castData = mCastList.get(position);

        holder.personNameTV.setText(castData.getPersonName());

        mImageLoader = new PicassoLoader();
        mImageLoader.loadImage(holder.personAV
                , NetworkUtils.getPosterImageURL(castData.getPersonImageUrlPath())
                , castData.getPersonName());
    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

}
