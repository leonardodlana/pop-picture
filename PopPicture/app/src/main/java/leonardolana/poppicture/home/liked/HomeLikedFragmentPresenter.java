package leonardolana.poppicture.home.liked;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.PictureLoaderHelper;

/**
 * Created by leonardolana on 7/27/18.
 */

public class HomeLikedFragmentPresenter extends BasePresenter {

    private HomeLikedFragmentView mView;
    private final PictureLoaderHelper mPicturesLoaderHelper;

    public HomeLikedFragmentPresenter(HomeLikedFragmentView view, PictureLoaderHelper picturesLoaderHelper) {
        mView = view;
        mPicturesLoaderHelper = picturesLoaderHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load pictures
        mView.showLoading();
        mPicturesLoaderHelper.loadFromLikedPictures(new PictureLoaderHelper.OnPicturesLoadListener() {
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
        mPicturesLoaderHelper.loadFromLikedPictures(new PictureLoaderHelper.OnPicturesLoadListener() {
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
