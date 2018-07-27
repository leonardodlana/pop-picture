package leonardolana.poppicture.home.nearby;

import java.util.List;

import leonardolana.poppicture.data.Picture;

/**
 * Created by leonardolana on 7/24/18.
 */

public interface HomeNearbyFragmentView {
    void showLoading();

    void hideLoading();

    void onLoad(List<Picture> pictures);

    void showLoadError();
}
