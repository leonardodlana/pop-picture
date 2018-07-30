package leonardolana.poppicture.helpers.api;

import java.util.List;

import leonardolana.poppicture.data.Picture;

/**
 * Created by leonardolana on 7/27/18.
 */

public interface PictureLoaderHelper {

    public interface OnPicturesLoadListener {
        void onLoad(List<Picture> pictures);

        void onError();
    }

    void loadNearbyPictures(OnPicturesLoadListener listener);

    void loadFromLikedPictures(OnPicturesLoadListener listener);

}
