package leonardolana.poppicture.profile;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.data.TrackingEvent;
import leonardolana.poppicture.helpers.api.PersistentHelper;
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
public class ProfileOnboardingDialogFragmentPresenter extends BasePresenter {

    private ProfileOnboardingDialogFragmentView mView;
    private PersistentHelper mPersistentHelper;
    private TrackingHelper mTrackingHelper;

    public ProfileOnboardingDialogFragmentPresenter(ProfileOnboardingDialogFragmentView view,
                                                    PersistentHelper persistentHelper,
                                                    TrackingHelper trackingHelper) {
        mView = view;
        mPersistentHelper = persistentHelper;
        mTrackingHelper = trackingHelper;
    }

    @Override
    public void onDestroy() {
        mView = null;
        mPersistentHelper = null;
        mTrackingHelper = null;
    }

    public void onSignInClick() {
        mTrackingHelper.log(TrackingEvent.PROFILE_ONBOARDING_CLICK_SIGN_IN);
        mView.launchAuthentication();
        disableOnboarding();
    }

    public void onDismissClick() {
        mTrackingHelper.log(TrackingEvent.PROFILE_ONBOARDING_CLICK_DISMISS);
        mView.dismiss();
        disableOnboarding();
    }

    private void disableOnboarding() {
        PersistentSharedKeys.setNeedToShowProfileOnboarding(mPersistentHelper, false);
    }
}
