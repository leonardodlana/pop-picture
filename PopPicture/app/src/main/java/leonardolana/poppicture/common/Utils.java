package leonardolana.poppicture.common;

import android.graphics.Bitmap;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

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
public class Utils {

    public static String randomName() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(128, secureRandom).toString(32);
    }

    public static int calculateSampleSize(int dstWidth, int dstHeight, int srcWidth, int srcHeight) {
        int newWidth = dstWidth;
        int newHeight = dstHeight;
        float scale;

        if (srcWidth == newWidth && srcHeight == newHeight) {
            return 1;
        } else if (srcWidth > srcHeight) {
            scale = srcWidth / srcHeight;
            newHeight = (int) (newWidth / scale);
        } else {
            scale = srcHeight / srcWidth;
            newWidth = (int) (newHeight / scale);
        }

        if (newWidth == srcWidth && newHeight == srcHeight) {
            return 1;
        }

        int sampleSize = 2;

        while (srcWidth / sampleSize > newWidth ||
                srcHeight / sampleSize > newHeight) {
            sampleSize++;
        }

        return sampleSize;
    }

    /**
     * @param bitmap      the source bitmap
     * @param maxSizeInKB the max bytes on memory that the thumbnail may use
     * @return a thumbnail with the required specs
     */
    public static Bitmap createThumbnail(Bitmap bitmap, int maxSizeInKB) {
        final int maxSizeInBytes = maxSizeInKB * 1024;
        final int allocationByteCount = bitmap.getAllocationByteCount();

        if (allocationByteCount < maxSizeInBytes)
            return bitmap;

        float scale = (float) maxSizeInBytes / (float) allocationByteCount;
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale), false);
    }

    public static String generateSHA256(String string) {
        HashCode hashCode = Hashing.sha256().hashString(string, StandardCharsets.UTF_8);
        return hashCode.toString();
    }
}
