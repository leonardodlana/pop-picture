package leonardolana.poppicture.onboarding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseDialogFragment;
import leonardolana.poppicture.common.PermissionWatcher;
import leonardolana.poppicture.data.Permission;
import leonardolana.poppicture.helpers.PermissionHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelper;

/**
 * Created by leonardolana on 7/26/18.
 */

public class OnboardingFragment extends BaseDialogFragment implements OnboardingFragmentView, PermissionWatcher {

    private OnboardingFragmentPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        setCancelable(false);

        mPresenter = new OnboardingFragmentPresenter(this, new PersistentHelper(getContext()));
        // It's important to call init with the view model,
        // this way we don't need to handle lifecycle on each fragment
        init(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // >>>>>>> View methods

    @OnClick(R.id.positive_button)
    public void onAgreeClick() {
        mPresenter.onAgreeClick();
    }

    @OnClick(R.id.negative_button)
    public void onDisagreeClick() {
        mPresenter.onDisagreeClick();
    }

    @Override
    public boolean checkPermission(Permission permission) {
        return PermissionHelper.isPermissionGranted(getContext(), permission);
    }

    @Override
    public void requestPermission(Permission permission) {
        PermissionHelper.requestPermission(getActivity(), this, permission);
    }

    // >>>>>>> Permission Watcher methods

    @Override
    public void onPermissionGranted(Permission permission) {
        mPresenter.onPermissionGranted(permission);
    }

    @Override
    public void onPermissionDenied(Permission permission) {
        mPresenter.onPermissionDenied(permission);
    }
}
