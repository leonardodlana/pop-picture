package leonardolana.poppicture.helpers.api;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.lang.annotation.Retention;

import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.data.User;

import static java.lang.annotation.RetentionPolicy.SOURCE;

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

public abstract class CacheHelper {

    @Retention(SOURCE)
    @IntDef({LOAD_ERROR_NO_CONNECTION, LOAD_ERROR_INVALID_ITEM, LOAD_ERROR_UNKNOWN})
    public @interface LoadError {}

    public static final int LOAD_ERROR_UNKNOWN = 0;
    public static final int LOAD_ERROR_NO_CONNECTION = 1;
    public static final int LOAD_ERROR_INVALID_ITEM = 2;

    public interface OnLoadPicture {
        void onLoad(Bitmap bitmap);
        void onError(@LoadError int error);
    }

    public abstract void loadPicture(Picture picture, boolean thumbnail, OnLoadPicture onLoadPicture);
    public abstract void loadPicture(Picture picture, boolean thumbnail, ImageView target);

}
