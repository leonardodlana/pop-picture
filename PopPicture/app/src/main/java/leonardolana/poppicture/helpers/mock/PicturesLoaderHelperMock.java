package leonardolana.poppicture.helpers.mock;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.PictureLoader;

/**
 * Created by leonardolana on 7/27/18.
 */

public class PicturesLoaderHelperMock implements PictureLoader {
    @Override
    public void loadNearbyPictures(Location location, OnPicturesLoadListener listener) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void loadNearbyPictures(final OnPicturesLoadListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onLoad(generateFakeList());
            }
        }, 1500);
    }

    @Override
    public void loadFromLikedPictures(Location location, OnPicturesLoadListener listener) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void loadFromLikedPictures(final OnPicturesLoadListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onLoad(generateFakeList());
            }
        }, 1500);
    }

    private List<Picture> generateFakeList() {
        List<Picture> pictureList = new ArrayList<>();
        Random rnd = new Random();
        int count = 5 + rnd.nextInt(99);
        for (int i = 0; i < count; i++) {
            pictureList.add(new Picture());
        }

        return pictureList;
    }
}
