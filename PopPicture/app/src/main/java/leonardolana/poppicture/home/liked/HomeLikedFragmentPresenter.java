package leonardolana.poppicture.home.liked;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.LocationListener;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.LocationHelper;
import leonardolana.poppicture.helpers.api.PictureLoader;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.home.nearby.HomeNearbyFragmentView;
import leonardolana.poppicture.server.RequestError;

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
 *
 */

public class HomeLikedFragmentPresenter extends BasePresenter {

    private HomeLikedFragmentView mView;
    private UserHelper mUserHelper;
    private LocationHelper mLocationHelper;
    private PictureLoader mPicturesLoaderHelper;

    public HomeLikedFragmentPresenter(HomeLikedFragmentView view, UserHelper userHelper, LocationHelper locationHelper, PictureLoader picturesLoaderHelper) {
        mView = view;
        mUserHelper = userHelper;
        mLocationHelper = locationHelper;
        mPicturesLoaderHelper = picturesLoaderHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load pictures
        mView.showLoading();

        if(!mUserHelper.hasLastKnownLocation()) {
            mLocationHelper.updateLocation(new LocationListener() {
                @Override
                public void onLocationKnown(Location location) {
                    loadPictures(location);
                }

                @Override
                public void onLocationNotFound() {

                }
            });
        } else {
            loadPictures(mUserHelper.getLastKnownLocation());
        }
    }

    private void loadPictures(Location location) {
        mPicturesLoaderHelper.loadFromLikedPictures(location, new PictureLoader.OnPicturesLoadListener() {
            @Override
            public void onLoad(List<Picture> pictures) {
                mView.onLoad(pictures);
                mView.hideLoading();
            }

            @Override
            public void onError(RequestError e) {
                mView.showLoadError();
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
        mUserHelper = null;
        mLocationHelper = null;
        mPicturesLoaderHelper = null;
    }

    public void refresh() {
        mPicturesLoaderHelper.loadFromLikedPictures(new PictureLoader.OnPicturesLoadListener() {
            @Override
            public void onLoad(List<Picture> pictures) {
                mView.onLoad(pictures);
            }

            @Override
            public void onError(RequestError e) {
                mView.showLoadError();
            }
        });
    }

    public void onPictureClick(Picture picture) {
        // Although this seems redundant, we can use this for tracking
        mView.openPicture(picture);
    }
}
