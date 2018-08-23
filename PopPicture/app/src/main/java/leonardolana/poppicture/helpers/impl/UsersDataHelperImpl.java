package leonardolana.poppicture.helpers.impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import leonardolana.poppicture.data.User;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.api.UsersDataHelper;
import leonardolana.poppicture.server.RequestError;
import leonardolana.poppicture.server.ServerRequestGetUser;

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
public class UsersDataHelperImpl implements UsersDataHelper {

    private static UsersDataHelperImpl INSTANCE;

    public static UsersDataHelperImpl getInstance(ServerHelper serverHelper, UserHelper userHelper) {
        if (INSTANCE == null)
            INSTANCE = new UsersDataHelperImpl(serverHelper, userHelper);

        return INSTANCE;
    }

    private Map<String, User> mUsersData = new HashMap<>();
    private ServerHelper mServerHelper;
    private UserHelper mUserHelper;

    private UsersDataHelperImpl(ServerHelper serverHelper, UserHelper userHelper) {
        mServerHelper = serverHelper;
        mUserHelper = userHelper;
    }

    @Override
    public void getUser(String publicId, @NonNull final GetUserResponse callback) {
        if (mUsersData.containsKey(publicId)) {
            callback.onSuccess(mUsersData.get(publicId));
            return;
        }

        new ServerRequestGetUser(publicId).execute(mServerHelper, mUserHelper, new ServerRequestGetUser.ServerRequestGerUserResponse() {
            @Override
            public void onSuccess(String publicId, String userName, String profilePictureName) {
                User user = new User(publicId, userName, profilePictureName);
                mUsersData.put(publicId, user);
                callback.onSuccess(user);
            }

            @Override
            public void onError(RequestError e) {
                callback.onError();
            }
        });
    }


}
