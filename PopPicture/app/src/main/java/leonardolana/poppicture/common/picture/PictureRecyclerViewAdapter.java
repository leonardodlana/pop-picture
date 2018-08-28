package leonardolana.poppicture.common.picture;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leonardolana.poppicture.R;
import leonardolana.poppicture.common.Utils;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.data.User;
import leonardolana.poppicture.helpers.api.CacheHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.api.UsersDataHelper;

/**
 * Created by Leonardo Lana
 * Github: https://github.com/leonardodlana
 * <p>
 * Copyright 2018 Leonardo Lana
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PictureRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnPictureClickListener {
        void onClick(Picture picture);
    }

    private static final int VIEW_TYPE_SMALL = 0;
    private static final int VIEW_TYPE_MEDIUM = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private final CacheHelper mCacheHelper;
    private final DecimalFormat mDecimalFormat;
    private final List<Pair<Integer, Picture>> mData = new ArrayList<>();
    private OnPictureClickListener mListener;

    public PictureRecyclerViewAdapter(CacheHelper cacheHelper) {
        mCacheHelper = cacheHelper;
        mDecimalFormat = new DecimalFormat("#.##");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;

        switch (viewType) {
            case VIEW_TYPE_SMALL:
                layout = R.layout.picture_small_view;
                break;
            case VIEW_TYPE_MEDIUM:
                layout = R.layout.picture_medium_view;
                break;
            case VIEW_TYPE_BIG:
                layout = R.layout.picture_big_view;
                break;
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int position) {
        PictureViewHolder holder = (PictureViewHolder) h;
        final Picture picture = mData.get(position).second;

        holder.mTextDistance.setText(String.format(holder.itemView.getContext().getString(R.string.distance_with_km), mDecimalFormat.format(picture.getDistanceInKM())));

        holder.mTextLike.setText(String.valueOf(picture.getLikesCount()));
        holder.setImageLikeTint(picture.isLiked());

        // Remove recycled reference
        holder.mImageView.setImageBitmap(null);
        holder.mImageView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.md_grey_600));
        mCacheHelper.loadPicture(picture, true, holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                    mListener.onClick(picture);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).first;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnPictureClickListener(OnPictureClickListener onPictureClickListener) {
        mListener = onPictureClickListener;
    }

    public void setData(List<Picture> pictureList) {
        mData.clear();
        designateLayout(pictureList);
        notifyDataSetChanged();
    }

    private void designateLayout(List<Picture> pictures) {
        Random rnd = new Random();
        // todo build rules
        for(Picture picture : pictures) {
            mData.add(new Pair<>(rnd.nextInt(3), picture));
        }
    }
}
