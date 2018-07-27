package leonardolana.poppicture.onboarding;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.Permission;

/**
 * Created by leonardolana on 7/26/18.
 */

public class OnboardingFragmentPresenter extends BasePresenter {

    private OnboardingFragmentView mView;

    public OnboardingFragmentPresenter(OnboardingFragmentView view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void onAgreeClick() {
        if(mView.checkPermission(Permission.LOCATION))
            mView.dismiss();
        else
            mView.requestPermission(Permission.LOCATION);
    }

    public void onDisagreeClick() {
        mView.dismiss();
    }
}
