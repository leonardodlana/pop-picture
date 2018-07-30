package leonardolana.poppicture.helpers.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.ref.WeakReference;

import leonardolana.poppicture.common.LocationListener;
import leonardolana.poppicture.data.Permission;
import leonardolana.poppicture.helpers.PermissionHelper;
import leonardolana.poppicture.helpers.api.LocationHelper;

/**
 * Created by leonardolana on 7/26/18.
 */

public class LocationHelperImpl implements LocationHelper {

    private FusedLocationProviderClient mFusedLocationClient;
    private WeakReference<Context> mContextReference;

    public LocationHelperImpl(Context context) {
        mContextReference = new WeakReference<>(context);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void updateLocation(final LocationListener locationListener) {
        Context context = mContextReference.get();

        if (context == null || !PermissionHelper.isPermissionGranted(context, Permission.LOCATION))
            return;

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            if(locationListener != null) {
                                locationListener.onLocationKnown(
                                        new leonardolana.poppicture.data.Location(location.getLatitude(),
                                                location.getLongitude()));
                            }
                        } else {
                            if(locationListener != null) {
                                locationListener.onLocationNotFound();
                            }
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        mFusedLocationClient = null;
        mContextReference = null;
    }
}
