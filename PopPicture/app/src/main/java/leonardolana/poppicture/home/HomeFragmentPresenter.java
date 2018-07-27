package leonardolana.poppicture.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.HomeSection;

/**
 * Created by leonardolana on 7/19/18.
 */

public class HomeFragmentPresenter extends BasePresenter {

    private HomeFragmentView mView;

    public HomeFragmentPresenter(HomeFragmentView view) {
        mView = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            mView.setSections(HomeSection.values());
        }

        // check if onboarding is necessary
        if(true) {
            boolean success = mView.showOnboarding();
        } else {

        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    void onClickMenu(String menuItemName) {
        for(HomeSection homeSection : HomeSection.values()) {
            if(TextUtils.equals(homeSection.getMenu(), menuItemName)) {
                // Do something with the click, tracking maybe
                // and send back to the view
                mView.onClickSection(homeSection);
                break;
            }
        }
    }

}
