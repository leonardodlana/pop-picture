package leonardolana.poppicture.viewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.AlertDialog;
import leonardolana.poppicture.common.BaseDialogFragment;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.CacheHelper;
import leonardolana.poppicture.helpers.impl.CacheHelperImpl;

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

public class ViewerFragment extends BaseDialogFragment implements ViewerFragmentView {

    public static ViewerFragment newInstance(Picture picture) {
        ViewerFragment viewerFragment = new ViewerFragment();
        viewerFragment.setPicture(picture);
        return viewerFragment;
    }

    private ViewerFragmentPresenter mPresenter;
    private Picture mPicture;

    @BindView(R.id.image_view)
    ImageView mImageView;

    @BindView(R.id.loading)
    ProgressBar mProgressBarLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen(true);
        setHasTitle(false);
        mPresenter = new ViewerFragmentPresenter(this);
        init(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CacheHelper cacheHelper = CacheHelperImpl.getInstance(getContext().getApplicationContext());
        cacheHelper.loadPicture(mPicture, false, new CacheHelper.OnLoadPicture() {
            @Override
            public void onLoad(Bitmap bitmap) {
                mImageView.setImageBitmap(bitmap);
                mProgressBarLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError(@CacheHelper.LoadError int error) {
                // todo handle each error
                AlertDialog dialog = AlertDialog.newInstance("Error loading the file", "Sorry, we couldn't load your file, please try again.");
                dialog.setCancelable(false);
                dialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dismiss();
                    }
                });
                dialog.show(getFragmentManager(), "dialog");
            }
        });
    }

    private void setPicture(Picture picture) {
        mPicture = picture;
    }

}
