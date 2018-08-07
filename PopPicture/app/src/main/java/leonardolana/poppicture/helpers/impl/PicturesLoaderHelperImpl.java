package leonardolana.poppicture.helpers.impl;

import java.util.List;

import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.PictureLoader;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.server.RequestError;
import leonardolana.poppicture.server.ServerRequestLikedPictures;
import leonardolana.poppicture.server.ServerRequestNearbyPictures;

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
public class PicturesLoaderHelperImpl implements PictureLoader {

    private final ServerHelper mServerHelper;
    private final UserHelper mUserHelper;

    public PicturesLoaderHelperImpl(ServerHelper serverHelper, UserHelper userHelper) {
        mServerHelper = serverHelper;
        mUserHelper = userHelper;
    }

    @Override
    public void loadNearbyPictures(Location location, final OnPicturesLoadListener listener) {
        new ServerRequestNearbyPictures(location).execute(mServerHelper, mUserHelper, new ServerRequestNearbyPictures.ServerRequestNearbyPicturesResponse() {
            @Override
            public void onSuccess(List<Picture> pictureList) {
                listener.onLoad(pictureList);
            }

            @Override
            public void onError(RequestError e) {
                listener.onError(e);
            }
        });
    }

    @Override
    public void loadNearbyPictures(OnPicturesLoadListener listener) {
        loadNearbyPictures(mUserHelper.getLastKnownLocation(), listener);
    }

    @Override
    public void loadFromLikedPictures(Location location, final OnPicturesLoadListener listener) {
        new ServerRequestLikedPictures(location).execute(mServerHelper, mUserHelper, new ServerRequestLikedPictures.ServerRequestLikedPicturesResponse() {
            @Override
            public void onSuccess(List<Picture> pictureList) {
                listener.onLoad(pictureList);
            }

            @Override
            public void onError(RequestError e) {
                listener.onError(e);
            }
        });
    }

    @Override
    public void loadFromLikedPictures(OnPicturesLoadListener listener) {
        loadFromLikedPictures(mUserHelper.getLastKnownLocation(), listener);
    }
}
