package leonardolana.poppicture.helpers.impl;

import java.util.HashMap;
import java.util.Map;

import leonardolana.poppicture.data.User;
import leonardolana.poppicture.helpers.api.UsersDataHelper;

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

    private Map<String,User> mUsersData = new HashMap<>();

    @Override
    public User getUser(String publicId) {
        return mUsersData.get(publicId);
    }

    @Override
    public void createUser(String publicId) {
        if(mUsersData.containsKey(publicId))
            return;

        User user = new User(publicId);
        mUsersData.put(publicId, user);
    }

    @Override
    public void updateUser(String publicId, String name, String profilePicture) {

    }
}
