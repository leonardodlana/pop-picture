package leonardolana.poppicture.helpers.api;

import java.util.List;

import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
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
 */

public interface PictureLoader {

    interface OnPicturesLoadListener {
        void onLoad(List<Picture> pictures);

        void onError(RequestError e);
    }

    void loadNearbyPictures(Location location, OnPicturesLoadListener listener);

    void loadNearbyPictures(OnPicturesLoadListener listener);

    void loadFromLikedPictures(Location location, OnPicturesLoadListener listener);

    void loadFromLikedPictures(OnPicturesLoadListener listener);

}
