package leonardolana.poppicture.onboarding;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.Permission;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.helpers.api.PersistentHelper;

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

public class OnboardingFragmentPresenter extends BasePresenter {

    private OnboardingFragmentView mView;
    private PersistentHelper mPersistentHelper;

    public OnboardingFragmentPresenter(OnboardingFragmentView view, PersistentHelper persistentHelper) {
        mView = view;
        mPersistentHelper = persistentHelper;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    private void dismiss() {
        PersistentSharedKeys.setNeedToShowOnboarding(mPersistentHelper, false);
        mView.dismiss();
    }

    void onClickAgree() {
        if (mView.checkPermission(Permission.LOCATION))
            dismiss();
        else
            mView.requestPermission(Permission.LOCATION);
    }

    void onPermissionGranted(Permission permission) {
        dismiss();
    }

    void onPermissionDenied(Permission permission) {
        //TODO explain
    }
}
