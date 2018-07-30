package leonardolana.poppicture.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.HomeSection;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.helpers.api.PersistentHelper;

/**
 * Created by leonardolana on 7/19/18.
 */

public class HomeFragmentPresenter extends BasePresenter {

    private HomeFragmentView mView;
    private PersistentHelper mPersistentHelper;

    public HomeFragmentPresenter(HomeFragmentView view, PersistentHelper persistentHelper) {
        mView = view;
        mPersistentHelper = persistentHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mView.setSections(HomeSection.values());
        }

        // check if onboarding is necessary
        if (mPersistentHelper.getBoolean(PersistentSharedKeys.KEY_NEEDS_TO_SHOW_ONBOARDING, true)) {
            mView.showOnboarding();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    void onClickMenu(String menuItemName) {
        for (HomeSection homeSection : HomeSection.values()) {
            if (TextUtils.equals(homeSection.getMenu(), menuItemName)) {
                // Do something with the click, tracking maybe
                // and send back to the view
                mView.onClickSection(homeSection);
                break;
            }
        }
    }

}
