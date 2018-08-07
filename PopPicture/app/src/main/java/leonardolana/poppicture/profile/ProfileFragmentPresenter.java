package leonardolana.poppicture.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.UserWatcher;
import leonardolana.poppicture.helpers.api.UserHelper;

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

public class ProfileFragmentPresenter extends BasePresenter {

    private UserHelper mUserHelper;
    private ProfileFragmentView mView;
    private UserWatcher mUserWatcher;
    private boolean mUserLoggedIn = false;

    public ProfileFragmentPresenter(ProfileFragmentView view, UserHelper userHelper) {
        mView = view;
        mUserHelper = userHelper;
        mUserLoggedIn = mUserHelper.isUserLoggedIn();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView.setEditEnabled(mUserLoggedIn);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mUserHelper.removeWatcher(mUserWatcher);
        mUserHelper = null;
    }

    public void onUpdateClick() {
        if (false && mUserLoggedIn) {
            // Call update
        } else {
            // Sign in
            mUserWatcher = new UserWatcher() {
                @Override
                public void onUserLoggedIn() {
                    mView.setEditEnabled(true);
                    mUserHelper.removeWatcher(mUserWatcher);
                }
            };
            mUserHelper.addWatcher(mUserWatcher);
            mView.launchAuthentication();
        }
    }
}
