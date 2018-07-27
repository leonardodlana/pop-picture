package leonardolana.poppicture.home.nearby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.LocationListener;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.api.PicturesLoaderHelperInterface;
import leonardolana.poppicture.helpers.impl.LocationHelper;
import leonardolana.poppicture.helpers.impl.PicturesLoaderHelper;

/**
 * Created by leonardolana on 7/24/18.
 */

public class HomeNearbyFragmentPresenter extends BasePresenter {

    private HomeNearbyFragmentView mView;
    private final PicturesLoaderHelperInterface mPicturesLoaderHelper;

    public HomeNearbyFragmentPresenter(HomeNearbyFragmentView view, PicturesLoaderHelperInterface picturesLoaderHelper) {
        mView = view;
        mPicturesLoaderHelper = picturesLoaderHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load pictures
        mView.showLoading();
        mPicturesLoaderHelper.loadNearbyPictures(new PicturesLoaderHelperInterface.OnPicturesLoadListener() {
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

}
