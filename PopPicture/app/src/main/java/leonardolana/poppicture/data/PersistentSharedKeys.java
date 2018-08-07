package leonardolana.poppicture.data;

import android.text.TextUtils;

import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;

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

public class PersistentSharedKeys {

    private static final String KEY_NEED_TO_SHOW_ONBOARDING = "need_to_show_onboarding";

    public static boolean needToShowOnboarding(PersistentHelper persistentHelper) {
        return persistentHelper.getBoolean(KEY_NEED_TO_SHOW_ONBOARDING, true);
    }

    public static void setNeedToShowOnboarding(PersistentHelper persistentHelper, boolean value) {
        persistentHelper.setBoolean(KEY_NEED_TO_SHOW_ONBOARDING, value);
    }

}
