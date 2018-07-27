package leonardolana.poppicture.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import leonardolana.poppicture.common.PermissionWatcher;
import leonardolana.poppicture.data.Permission;

/**
 * Created by leonardolana on 7/26/18.
 */

public class PermissionHelper {

    private static final int REQUEST_CODE = 1001;

    /**
     * This static field will keep a reference to any watcher that is waiting
     * for a permission result.
     * <p>
     * Weak reference is used to avoid any leaks.
     * <p>
     * Possible alternative: use a local broadcast
     */
    private static List<WeakReference<PermissionWatcher>> WATCHERS = new ArrayList<>();

    public static boolean isPermissionGranted(Context context, Permission permission) {
        return ContextCompat.checkSelfPermission(context, permission.getName()) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, @Nullable PermissionWatcher permissionWatcher, Permission... permissions) {
        if (permissions.length == 0) {
            throw new IllegalArgumentException("Permission must be >= 1");
        }

        String[] stringPermissions = new String[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            stringPermissions[i] = permissions[i].getName();
        }

        ActivityCompat.requestPermissions(activity, stringPermissions, REQUEST_CODE);

        if (permissionWatcher != null) {
            WATCHERS.add(new WeakReference<PermissionWatcher>(permissionWatcher));
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != REQUEST_CODE)
            return;

        String permissionName;
        boolean granted;
        PermissionWatcher permissionWatcher;
        Permission permission;

        for (int i = 0; i < permissions.length; i++) {
            permissionName = permissions[i];
            granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            permission = Permission.fromName(permissionName);

            for(WeakReference<PermissionWatcher> weakReference : WATCHERS) {
                permissionWatcher = weakReference.get();
                if(permissionWatcher != null) {
                    if(granted)
                        permissionWatcher.onPermissionGranted(permission);
                    else
                        permissionWatcher.onPermissionDenied(permission);
                }
            }
        }

        WATCHERS.clear();
    }
}
