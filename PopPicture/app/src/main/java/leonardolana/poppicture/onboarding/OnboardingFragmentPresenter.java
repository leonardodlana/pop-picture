package leonardolana.poppicture.onboarding;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.Permission;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.helpers.api.PersistentHelper;

/**
 * Created by leonardolana on 7/26/18.
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
        mPersistentHelper.setBoolean(PersistentSharedKeys.KEY_NEEDS_TO_SHOW_ONBOARDING, false);
        mView.dismiss();
    }

    void onAgreeClick() {
        if (mView.checkPermission(Permission.LOCATION))
            dismiss();
        else
            mView.requestPermission(Permission.LOCATION);
    }

    void onDisagreeClick() {
    }

    void onPermissionGranted(Permission permission) {
        dismiss();
    }

    void onPermissionDenied(Permission permission) {
        //TODO explain
    }
}
