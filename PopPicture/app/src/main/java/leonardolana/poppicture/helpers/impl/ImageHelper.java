package leonardolana.poppicture.helpers.impl;

import android.graphics.Bitmap;
import android.widget.ImageView;

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
public class ImageHelper {

    public static Bitmap resize(ImageView dstView, Bitmap bitmap) {
        float iWidth = bitmap.getWidth();
        float iHeight = bitmap.getHeight();
        int newWidth = dstView.getMeasuredWidth();
        int newHeight = newWidth;

        float scale;

        if (iWidth > iHeight) {
            scale = iWidth / iHeight;
            newHeight = (int) (dstView.getMeasuredWidth() / scale);
        } else {
            scale = iHeight / iWidth;
            newWidth = (int) (newHeight / scale);
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }

}
