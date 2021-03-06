package leonardolana.poppicture.onboarding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseDialogFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.PermissionWatcher;
import leonardolana.poppicture.data.Permission;
import leonardolana.poppicture.helpers.PermissionHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;

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

public class OnboardingFragment extends BaseDialogFragment implements OnboardingFragmentView, PermissionWatcher {

    private OnboardingFragmentPresenter mPresenter;

    @BindView(R.id.text_location_and_terms)
    TextView mTextLocationAndTerms;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasTitle(false);
        setCancelable(false);

        mPresenter = new OnboardingFragmentPresenter(this, PersistentHelperImpl.getInstance(getContext().getApplicationContext()));
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Enable click on links
        mTextLocationAndTerms.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // >>>>>>> View methods

    @OnClick(R.id.button_positive)
    public void onClickAgree() {
        mPresenter.onClickAgree();
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
