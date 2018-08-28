package leonardolana.poppicture.home.liked;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.picture.PictureRecyclerView;
import leonardolana.poppicture.common.picture.PictureRecyclerViewAdapter;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.CacheHelper;
import leonardolana.poppicture.helpers.api.LocationHelper;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.CacheHelperImpl;
import leonardolana.poppicture.helpers.impl.LocationHelperImpl;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.PicturesLoaderHelperImpl;
import leonardolana.poppicture.helpers.impl.ServerHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;
import leonardolana.poppicture.helpers.mock.PicturesLoaderHelperMock;
import leonardolana.poppicture.home.nearby.HomeNearbyFragmentPresenter;
import leonardolana.poppicture.viewer.ViewerFragment;

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

public class HomeLikedFragment extends BaseFragment implements HomeLikedFragmentView {

    private HomeLikedFragmentPresenter mPresenter;
    private PictureRecyclerViewAdapter mAdapter;

    @BindView(R.id.loading)
    ProgressBar mProgressBarLoading;

    @BindView(R.id.pictures_recycler_view)
    PictureRecyclerView mPicturesRecyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context applicationContext = getContext().getApplicationContext();

        ServerHelper serverHelper = ServerHelperImpl.getInstance(applicationContext);
        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(applicationContext);
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);
        LocationHelper locationHelper = new LocationHelperImpl(applicationContext, userHelper);

        CacheHelper cacheHelper = CacheHelperImpl.getInstance(applicationContext);
        mAdapter = new PictureRecyclerViewAdapter(cacheHelper);
        mPresenter = new HomeLikedFragmentPresenter(this, userHelper, locationHelper, new PicturesLoaderHelperImpl(serverHelper, userHelper));
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_liked, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPicturesRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnPictureClickListener(new PictureRecyclerViewAdapter.OnPictureClickListener() {
            @Override
            public void onClick(Picture picture) {
                mPresenter.onPictureClick(picture);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void showLoading() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoad(List<Picture> pictures) {
        mAdapter.setData(pictures);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadError() {
        // TODO
    }

    @Override
    public void openPicture(Picture picture) {
        ViewerFragment viewerFragment = ViewerFragment.newInstance(picture);
        viewerFragment.show(getFragmentManager(), "dialog");
    }
}
