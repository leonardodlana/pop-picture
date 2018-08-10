package leonardolana.poppicture.helpers.impl;

import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import leonardolana.poppicture.common.UserWatcher;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.helpers.api.PersistentHelper;
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
 * <p>
 * <p>
 * <p>
 * <p>
 * I chose to use this class as a singleton because user information is needed across
 * the entire application.
 */


public class UserHelperImpl implements UserHelper {

    private static UserHelper INSTANCE;

    public static UserHelper getInstance(PersistentHelper helper) {
        if (INSTANCE == null)
            INSTANCE = new UserHelperImpl(helper);

        return INSTANCE;
    }

    private static final String KEY_USER_FIREBASE_ID = "user_firebase_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_LATITUDE = "user_latitude";
    private static final String KEY_USER_LONGITUDE = "user_longitude";
    private static final String KEY_USER_PUBLIC_ID = "user_public_id";

    private final PersistentHelper mPersistentHelper;

    //Since this class is a singleton, it's a good practice to not maintain strong references
    private List<WeakReference<UserWatcher>> mUserWatchers = new ArrayList<>();

    private UserHelperImpl(PersistentHelper persistentHelper) {
        mPersistentHelper = persistentHelper;
    }

    @Override
    public void setName(String name) {
        mPersistentHelper.setString(KEY_USER_NAME, name);
        onUserNameChanged(name);
    }

    @Override
    public String getName() {
        return mPersistentHelper.getString(KEY_USER_NAME, "");
    }

    @Override
    public void setFirebaseId(String firebaseId) {
        mPersistentHelper.setString(KEY_USER_FIREBASE_ID, firebaseId);
        onUserLoggedIn();
    }

    @Override
    public String getFirebaseId() {
        return mPersistentHelper.getString(KEY_USER_FIREBASE_ID, "");
    }

    @Override
    public void setEmail(String email) {
        mPersistentHelper.setString(KEY_USER_EMAIL, email);
    }

    @Override
    public String getEmail() {
        return mPersistentHelper.getString(KEY_USER_EMAIL, "");
    }

    @Override
    public boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(getFirebaseId());
    }

    @Override
    public void setLastKnownLocation(Location lastKnownLocation) {
        mPersistentHelper.setDouble(KEY_USER_LATITUDE, lastKnownLocation.getLatitude());
        mPersistentHelper.setDouble(KEY_USER_LONGITUDE, lastKnownLocation.getLongitude());
        onUserLocationChanged(lastKnownLocation);
    }

    @Override
    public Location getLastKnownLocation() {
        double latitude = mPersistentHelper.getDouble(KEY_USER_LATITUDE, 0);
        double longitude = mPersistentHelper.getDouble(KEY_USER_LONGITUDE, 0);
        return new Location(latitude, longitude);
    }

    @Override
    public boolean hasLastKnownLocation() {
        Location location = getLastKnownLocation();
        return !(location.getLatitude() == 0 &&
                location.getLongitude() == 0);
    }

    @Override
    public void setPublicId(String publicId) {
        mPersistentHelper.setString(KEY_USER_PUBLIC_ID, publicId);
    }

    @Override
    public String getPublicId() {
        return mPersistentHelper.getString(KEY_USER_PUBLIC_ID, "");
    }

    /*
        Watcher methods
     */

    @Override
    public void onUserNameChanged(String name) {
        UserWatcher userWatcher;

        for (WeakReference<UserWatcher> userWatcherReference : mUserWatchers) {
            userWatcher = userWatcherReference.get();
            if (userWatcher != null)
                userWatcher.onNameChanged(name);
        }
    }

    @Override
    public void onUserLocationChanged(Location location) {
        UserWatcher userWatcher;

        for (WeakReference<UserWatcher> userWatcherReference : mUserWatchers) {
            userWatcher = userWatcherReference.get();
            if (userWatcher != null)
                userWatcher.onUserLocationChanged(location);
        }
    }

    @Override
    public void onUserLoggedIn() {
        UserWatcher userWatcher;

        for (WeakReference<UserWatcher> userWatcherReference : mUserWatchers) {
            userWatcher = userWatcherReference.get();
            if (userWatcher != null)
                userWatcher.onUserLoggedIn();
        }
    }

    @Override
    public void addWatcher(UserWatcher userWatcher) {
        mUserWatchers.add(new WeakReference<>(userWatcher));
    }

    @Override
    public void removeWatcher(UserWatcher toRemove) {
        UserWatcher userWatcher;

        for(int i=0;i<mUserWatchers.size();i++) {
            userWatcher = mUserWatchers.get(i).get();
            if(userWatcher == toRemove) {
                mUserWatchers.remove(i);
                break;
            }
        }
    }

}
