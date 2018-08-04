package leonardolana.poppicture.data;

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

/*
 * HomeSection is the bridge between the presenter and the view.
 */

public enum HomeSection {

    NEARBY("leonardolana.poppicture.home.nearby.HomeNearbyFragment", "action_nearby"),
    LIKED("leonardolana.poppicture.home.liked.HomeLikedFragment", "action_liked"),
    PROFILE("leonardolana.poppicture.profile.ProfileFragment", "action_profile");

    private final String mName;
    private final String mMenu;

    HomeSection(String name, String menu) {
        mName = name;
        mMenu = menu;
    }

    public String getName() {
        return mName;
    }

    public String getMenu() {
        return mMenu;
    }

}
