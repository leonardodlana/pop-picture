package leonardolana.poppicture.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import leonardolana.poppicture.common.Utils;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;

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
public class ServerRequestNearbyPictures extends ServerRequest implements RequestResponse {

    public interface ServerRequestNearbyPicturesResponse {
        void onSuccess(List<Picture> pictureList);
        void onError(RequestError e);
    }

    private ServerRequestNearbyPicturesResponse mCallback;
    private Location mLocation;

    public ServerRequestNearbyPictures(Location location) {
        super(ServerConstants.URL, "Picture.findNearby");
        mLocation = location;
        addParam(KEY_LATITUDE, location.getLatitude());
        addParam(KEY_LONGITUDE, location.getLongitude());
    }

    public void execute(ServerHelper serverHelper, UserHelper userHelper, ServerRequestNearbyPicturesResponse callback) {
        mCallback = callback;
        super.execute(serverHelper, userHelper, this);
    }

    // Avoid overload on refreshes
    @Override
    protected boolean shouldUseCacheIfAvailable() {
        return true;
    }

    @Override
    public void onRequestSuccess(String data) {
        try {
            List<Picture> pictureList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            Picture picture;
            float distanceInKM;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                picture = Picture.fromJSON(jsonObject);
                if (picture != null) {
                    //TODO in background, sqrt is heavy work
                    distanceInKM = Utils.distanceBetweenCoordinatesInKm(
                            mLocation.getLatitude(), mLocation.getLongitude(),
                            picture.getLatitude(), picture.getLongitude());
                    picture.setDistanceInKM(distanceInKM);
                    pictureList.add(picture);
                }
            }

            if (mCallback != null)
                mCallback.onSuccess(pictureList);
        } catch (Exception e) {
            e.printStackTrace();
            if (mCallback != null)
                mCallback.onError(RequestError.JSON_PARSE_ERROR);
        }
    }

    @Override
    public void onRequestError(RequestError error) {
        if (mCallback != null)
            mCallback.onError(error);
    }

}
