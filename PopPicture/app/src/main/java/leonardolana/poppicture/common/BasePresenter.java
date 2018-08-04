package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

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

public abstract class BasePresenter {

    /**
     * On screen create
     * @param savedInstanceState
     */
    public void onCreate(@Nullable Bundle savedInstanceState) {}

    /**
     * On screen resume
     */
    public void onResume() {}

    /**
     * On screen pause
     */
    public void onPause() {}

    /**
     * On save state, called before the destroy, usually
     * when the system needs memory
     * @param outState
     */

    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * On screen destroy
     */
    public abstract void onDestroy();

}
