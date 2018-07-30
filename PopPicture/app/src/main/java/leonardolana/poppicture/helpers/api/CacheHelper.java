package leonardolana.poppicture.helpers.api;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;

import leonardolana.poppicture.data.Picture;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by leonardolana on 7/29/18.
 */

public interface CacheHelper {

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

    public void loadPicture(Picture picture, @NonNull OnLoadPicture onLoadPicture);

}
