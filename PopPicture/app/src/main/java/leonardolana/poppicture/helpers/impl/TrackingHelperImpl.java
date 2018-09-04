package leonardolana.poppicture.helpers.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

import leonardolana.poppicture.data.TrackingEvent;
import leonardolana.poppicture.helpers.api.TrackingHelper;

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
public class TrackingHelperImpl implements TrackingHelper {

    private static TrackingHelper INSTANCE;

    public static TrackingHelper getInstance(Context context) {
        if(context instanceof Activity)
            throw new UnsupportedOperationException("To avoid leaks, use only application context");

        if(INSTANCE == null)
            INSTANCE = new TrackingHelperImpl(context);

        return INSTANCE;
    }

    private final Context mContext;

    public TrackingHelperImpl(Context context) {
        mContext = context;
    }

    @Override
    public void log(@NonNull TrackingEvent event, @Nullable Bundle params) {
        FirebaseAnalytics.getInstance(mContext).logEvent(event.getTrackingName(), params);
    }

    @Override
    public void log(@NonNull TrackingEvent event) {
        log(event, null);
    }

}
