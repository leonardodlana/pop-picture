package leonardolana.poppicture.common.picture;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leonardolana.poppicture.R;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.CacheHelper;
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

    private static final int VIEW_TYPE_SMALL = 0;
    private static final int VIEW_TYPE_MEDIUM = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private final CacheHelper mCacheHelper;
    private final UsersDataHelper mUsersDataHelper;
    private final List<Pair<Integer, Picture>> mData = new ArrayList<>();

    public PictureRecyclerViewAdapter(CacheHelper cacheHelper, UsersDataHelper usersDataHelper) {
        mCacheHelper = cacheHelper;
        mUsersDataHelper = usersDataHelper;
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
        Picture picture = mData.get(position).second;
        mCacheHelper.loadPicture(picture, holder.mImageView);
        holder.mImageView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.md_grey_600));
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).first;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Picture> pictureList) {
        mData.clear();
        designateLayout(pictureList);
        notifyDataSetChanged();
    }

    private void designateLayout(List<Picture> pictures) {
        Random rnd = new Random();

        for(Picture picture : pictures) {
            mData.add(new Pair<Integer, Picture>(rnd.nextInt(3), picture));
        }
    }
}
