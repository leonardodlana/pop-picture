package leonardolana.poppicture.helpers.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.ref.WeakReference;

import leonardolana.poppicture.common.LocationListener;
import leonardolana.poppicture.common.PermissionWatcher;
import leonardolana.poppicture.data.Permission;
import leonardolana.poppicture.helpers.PermissionHelper;
import leonardolana.poppicture.helpers.api.LocationHelper;
import leonardolana.poppicture.helpers.api.UserHelper;

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

public class LocationHelperImpl implements LocationHelper, PermissionWatcher {

    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * Avoid context leaking
     */
    private WeakReference<Context> mContextReference;
    private UserHelper mUserHelper;

    /**
     * Avoid holding a reference to a listener
     */
    private WeakReference<LocationListener> mListenerReference;

    public LocationHelperImpl(Context context, UserHelper userHelper) {
        mUserHelper = userHelper;
        mContextReference = new WeakReference<>(context);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void updateLocation(final LocationListener locationListener) {
        Context context = mContextReference.get();

        if (context == null)
            return;

        if(!PermissionHelper.isPermissionGranted(context, Permission.LOCATION)) {
            mListenerReference = new WeakReference<>(locationListener);
            PermissionHelper.addPermissionWatcher(this);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mFusedLocationClient.removeLocationUpdates(this);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    leonardolana.poppicture.data.Location lastKnownLocation = new leonardolana.poppicture.data.Location(location.getLatitude(),
                            location.getLongitude());
                    mUserHelper.setLastKnownLocation(lastKnownLocation);
                    if(locationListener != null) {
                        locationListener.onLocationKnown(lastKnownLocation);
                    }
                } else {
                    if(locationListener != null) {
                        locationListener.onLocationNotFound();
                    }
                }
            }
        }, null);
    }

    @Override
    public void destroy() {
        mFusedLocationClient = null;
        mContextReference = null;
    }

    @Override
    public void onPermissionGranted(Permission permission) {
        if(permission != Permission.LOCATION)
            return;

        if(mListenerReference != null) {
            LocationListener locationListener = mListenerReference.get();
            if(locationListener != null) {
                updateLocation(locationListener);
            }
        }
    }

    @Override
    public void onPermissionDenied(Permission permission) {
        if(permission != Permission.LOCATION)
            return;

        if(mListenerReference != null) {
            LocationListener locationListener = mListenerReference.get();
            if(locationListener != null) {
                locationListener.onLocationNotFound();
            }
        }
    }
}
