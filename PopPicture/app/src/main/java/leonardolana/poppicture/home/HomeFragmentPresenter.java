package leonardolana.poppicture.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.HomeSection;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.helpers.api.PersistentHelper;

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
        if (PersistentSharedKeys.needToShowOnboarding(mPersistentHelper)) {
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
