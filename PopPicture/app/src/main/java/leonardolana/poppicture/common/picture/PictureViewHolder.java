package leonardolana.poppicture.common.picture;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;

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

public class PictureViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    AppCompatImageView mImageView;

    @BindView(R.id.text_distance)
    TextView mTextDistance;

    @BindView(R.id.image_like)
    AppCompatImageView mImageLike;

    @BindView(R.id.text_like)
    TextView mTextLike;

    public PictureViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setImageLikeTint(boolean liked) {
        if(liked)
            mImageLike.setImageTintList(ColorStateList.valueOf(mImageLike.getResources().getColor(R.color.picture_liked)));
        else
            mImageLike.setImageTintList(ColorStateList.valueOf(mImageLike.getResources().getColor(R.color.picture_not_liked)));
    }
}
