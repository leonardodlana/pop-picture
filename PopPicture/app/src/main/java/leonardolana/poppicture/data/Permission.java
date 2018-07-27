package leonardolana.poppicture.data;

import android.Manifest;
import android.text.TextUtils;

/**
 * Created by leonardolana on 7/26/18.
 * Wrapper for permissions
 */

public enum Permission {
    LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
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
