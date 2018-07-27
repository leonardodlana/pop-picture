package leonardolana.poppicture.data;

/**
 * Created by leonardolana on 7/26/18.
 */

public class Location {

    private double mLatitude;
    private double mLongitude;

    public Location(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

}
