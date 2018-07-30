package leonardolana.poppicture.helpers.api;

import leonardolana.poppicture.common.LocationListener;

/**
 * Created by leonardolana on 7/26/18.
 */

public interface LocationHelper {

    public void updateLocation(LocationListener locationListener);

    public void destroy();

}
