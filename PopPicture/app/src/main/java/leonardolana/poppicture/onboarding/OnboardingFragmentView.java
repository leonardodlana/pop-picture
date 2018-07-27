package leonardolana.poppicture.onboarding;

import leonardolana.poppicture.data.Permission;

/**
 * Created by leonardolana on 7/26/18.
 */

public interface OnboardingFragmentView {
    void dismiss();

    boolean checkPermission(Permission permission);

    void requestPermission(Permission permission);
}
