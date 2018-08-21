package leonardolana.poppicture.viewer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.AlertDialog;
import leonardolana.poppicture.common.BaseDialogFragment;
import leonardolana.poppicture.common.ConfirmationDialog;
import leonardolana.poppicture.common.Utils;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.CacheHelper;
import leonardolana.poppicture.helpers.api.CloudStorage;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.CacheHelperImpl;
import leonardolana.poppicture.helpers.impl.CloudStorageImpl;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.ServerHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;

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

    private PersistentHelper mPersistentHelper;
    private UserHelper mUserHelper;
    private CacheHelper mCacheHelper;

    private ViewerFragmentPresenter mPresenter;
    private Picture mPicture;

    @BindView(R.id.image_view)
    ImageView mImageView;

    @BindView(R.id.loading)
    ProgressBar mProgressBarLoading;

    @BindView(R.id.button_delete)
    AppCompatImageView mButtonDelete;

    @BindView(R.id.button_like)
    AppCompatImageView mButtonLike;

    @BindView(R.id.text_title)
    TextView mTextTitle;

    @BindView(R.id.text_description)
    TextView mTextDescription;

    @BindView(R.id.text_distance)
    TextView mTextDistance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen(true);
        setHasTitle(false);

        Context applicationContext = getContext().getApplicationContext();

        mPersistentHelper = PersistentHelperImpl.getInstance(applicationContext);
        mUserHelper = UserHelperImpl.getInstance(mPersistentHelper);
        mCacheHelper = CacheHelperImpl.getInstance(applicationContext);
        ServerHelper serverHelper = ServerHelperImpl.getInstance(applicationContext);
        CloudStorage cloudStorage = new CloudStorageImpl();

        mPresenter = new ViewerFragmentPresenter(this, mPicture, serverHelper, mUserHelper, cloudStorage);
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

        mCacheHelper.loadPicture(mPicture, false, new CacheHelper.OnLoadPicture() {
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

        if (TextUtils.equals(mPicture.getUserId(), mUserHelper.getPublicId()))
            mButtonDelete.setVisibility(View.VISIBLE);

        mTextTitle.setText(mPicture.getTitle());
        mTextDescription.setText(mPicture.getDescription());

        refreshLike();

        Location userLocation = mUserHelper.getLastKnownLocation();

        mTextDistance.setText(Utils.distanceBetweenCoordinatesInKm(
                userLocation.getLatitude(), userLocation.getLongitude(),
                mPicture.getLatitude(), mPicture.getLongitude()));
    }

    @OnClick(R.id.button_close)
    public void onButtonCloseClick() {
        mPresenter.onCloseClick();
    }

    @OnClick(R.id.button_delete)
    public void onButtonDeleteClick() {
        // Confirm dialog then call presenter
        ConfirmationDialog dialog = ConfirmationDialog.newInstance("Are you sure?",
                "Deleting will erase permanently for you and others",
                "DELETE", "CANCEL");

        dialog.setOnConfirmationDialogListener(new ConfirmationDialog.OnConfirmationDialogListener() {
            @Override
            public void onClickPositive() {
                mPresenter.onDeleteClick();
            }

            @Override
            public void onClickNegative() {
                // ignore
            }
        });

        dialog.show(getFragmentManager(), "dialog");
    }

    @OnClick(R.id.button_like)
    public void onButtonLikeClick() {
        mPresenter.onLikeClick();
    }

    private void setPicture(Picture picture) {
        mPicture = picture;
    }

    /*
        View methods
     */

    @Override
    public void refreshLike() {
        if (mPicture.isLiked())
            mButtonLike.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.picture_liked)));
        else
            mButtonLike.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.picture_not_liked)));
    }

    @Override
    public void showDeleteError() {
        AlertDialog dialog = AlertDialog.newInstance("Error deleting the file", "Sorry, we couldn't delete your file, please try again.");
        dialog.setCancelable(false);
        dialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
            @Override
            public void onDismiss() {
//                dismiss();
            }
        });
        dialog.show(getFragmentManager(), "dialog");
    }
}
