package leonardolana.poppicture.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.UserWatcher;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.server.RequestError;
import leonardolana.poppicture.server.RequestResponse;
import leonardolana.poppicture.server.ServerRequestUpdateUser;

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

    private ProfileFragmentView mView;
    private UserHelper mUserHelper;
    private ServerHelper mServerHelper;
    private boolean mUserLoggedIn = false;
    private UserWatcher mUserWatcher;

    public ProfileFragmentPresenter(ProfileFragmentView view, UserHelper userHelper, ServerHelper serverHelper) {
        mView = view;
        mUserHelper = userHelper;
        mServerHelper = serverHelper;
        mUserLoggedIn = mUserHelper.isUserLoggedIn();
        if (!mUserLoggedIn) {
            // Register a watcher to get notified when the user
            // completed the authentication flow
            mUserWatcher = new UserWatcher() {
                @Override
                public void onUserLoggedIn() {
                    mView.setEditEnabled(true);
                    mView.setTextName(mUserHelper.getName());
                    mUserHelper.removeWatcher(mUserWatcher);
                }
            };
            mUserHelper.addWatcher(mUserWatcher);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView.setEditEnabled(mUserLoggedIn);
        if (mUserLoggedIn) {
            mView.setTextName(mUserHelper.getName());
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        mUserHelper.removeWatcher(mUserWatcher);
        mUserHelper = null;
    }

    public void onClickUpdate(String name) {
        if (mUserLoggedIn) {
            new ServerRequestUpdateUser(name).execute(mServerHelper, mUserHelper, new RequestResponse() {
                @Override
                public void onRequestSuccess(String data) {
                    mView.showUpdateFeedback();
                }

                @Override
                public void onRequestError(RequestError error) {
                    mView.showUpdateError(error);
                }
            });
        } else {
            mView.launchAuthentication();
        }
    }
}
