package leonardolana.poppicture.home.nearby;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.PictureLoaderHelper;

/**
 * Created by leonardolana on 7/24/18.
 */

public class HomeNearbyFragmentPresenter extends BasePresenter {

    private HomeNearbyFragmentView mView;
    private final PictureLoaderHelper mPicturesLoaderHelper;

    public HomeNearbyFragmentPresenter(HomeNearbyFragmentView view, PictureLoaderHelper picturesLoaderHelper) {
        mView = view;
        mPicturesLoaderHelper = picturesLoaderHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load pictures
        mView.showLoading();
        mPicturesLoaderHelper.loadNearbyPictures(new PictureLoaderHelper.OnPicturesLoadListener() {
            @Override
            public void onLoad(List<Picture> pictures) {
                mView.onLoad(pictures);
                mView.hideLoading();
            }

            @Override
            public void onError() {
                mView.showLoadError();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void refresh() {
        mPicturesLoaderHelper.loadNearbyPictures(new PictureLoaderHelper.OnPicturesLoadListener() {
            @Override
            public void onLoad(List<Picture> pictures) {
                mView.onLoad(pictures);
            }

            @Override
            public void onError() {
                mView.showLoadError();
            }
        });
    }
}
