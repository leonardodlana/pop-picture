package leonardolana.poppicture.common;

import leonardolana.poppicture.data.Location;

/**
 * Created by leonardolana on 7/26/18.
 */

public interface LocationListener {

    void onLocationKnown(Location location);

    void onLocationNotFound();
}
