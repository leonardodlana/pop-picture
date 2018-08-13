package leonardolana.poppicture.data;

import android.Manifest;
import android.text.TextUtils;

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
 * Wrapper for permissions
 */

public enum Permission {
    LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    CAMERA(Manifest.permission.CAMERA);

    private final String mName;

    Permission(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public static Permission fromName(String permissionName) {
        for(Permission permission : values()) {
            if(TextUtils.equals(permission.getName(), permissionName))
                return permission;
        }

        return null;
    }
}
