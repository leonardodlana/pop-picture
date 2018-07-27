package leonardolana.poppicture.home.nearby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.LocationListener;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.helpers.impl.LocationHelper;

/**
 * Created by leonardolana on 7/24/18.
 */

public class HomeNearbyFragmentPresenter extends BasePresenter {

    private HomeNearbyFragmentView mView;
    private LocationHelper mLocationHelper;

    public HomeNearbyFragmentPresenter(HomeNearbyFragmentView view, LocationHelper locationHelper) {
        mView = view;
        mLocationHelper = locationHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load pictures
        mView.showLoading();
        mLocationHelper.updateLocation(new LocationListener() {
            @Override
            public void onLocationKnown(Location location) {
                Log.e("wtf", location.getLatitude() + " " + location.getLongitude());
            }

            @Override
            public void onLocationNotFound() {
                // Location was not found, we must try again
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
