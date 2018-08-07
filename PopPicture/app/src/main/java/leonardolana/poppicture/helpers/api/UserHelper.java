package leonardolana.poppicture.helpers.api;

import leonardolana.poppicture.common.UserWatcher;
import leonardolana.poppicture.data.Location;

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
public interface UserHelper {

    public void setName(String name);

    public String getName();

    public void setFirebaseId(String firebaseId);

    public String getFirebaseId();

    public void setEmail(String email);

    public String getEmail();

    public boolean isUserLoggedIn();

    void setLastKnownLocation(Location lastKnownLocation);

    Location getLastKnownLocation();

    /*
        Watcher methods
     */

    void onUserNameChanged(String name);

    void onUserLocationChanged(Location location);

    void onUserLoggedIn();

    void addWatcher(UserWatcher userWatcher);

    void removeWatcher(UserWatcher userWatcher);

    boolean hasLastKnownLocation();
}
