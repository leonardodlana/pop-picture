package leonardolana.poppicture.common;

import leonardolana.poppicture.data.Permission;

/**
 * Created by leonardolana on 7/26/18.
 */

public interface PermissionWatcher {

    void onPermissionGranted(Permission permission);

    void onPermissionDenied(Permission permission);

}
