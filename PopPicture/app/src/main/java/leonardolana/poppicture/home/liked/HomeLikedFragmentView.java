package leonardolana.poppicture.home.liked;

import java.util.List;

import leonardolana.poppicture.data.Picture;

/**
 * Created by leonardolana on 7/27/18.
 */

public interface HomeLikedFragmentView {

    void showLoading();

    void hideLoading();

    void onLoad(List<Picture> pictures);

    void showLoadError();

}
