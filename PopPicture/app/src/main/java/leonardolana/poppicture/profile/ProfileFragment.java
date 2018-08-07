package leonardolana.poppicture.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;
import leonardolana.poppicture.login.LoginActivity;

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

public class ProfileFragment extends BaseFragment implements ProfileFragmentView {

    private ProfileFragmentPresenter mPresenter;
    @BindView(R.id.profile_image)
    ImageView mProfileImageView;

    @BindView(R.id.profile_name_input_layout)
    TextInputLayout mProfileNameInputLayout;

    @BindView(R.id.profile_name_input_edit_text)
    TextInputEditText mProfileNameInputEditText;

    @BindView(R.id.update_profile_button)
    TextView mUpdateProfileButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(getContext().getApplicationContext());
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);
        mPresenter = new ProfileFragmentPresenter(this, userHelper);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.update_profile_button)
    public void onClickUpdate() {
        mPresenter.onUpdateClick();
    }

    // View methods


    @Override
    public void setEditEnabled(boolean enabled) {
        mProfileImageView.setEnabled(enabled);
        mProfileNameInputLayout.setEnabled(enabled);
        mProfileNameInputEditText.setEnabled(enabled);
        mProfileNameInputEditText.setFocusable(enabled);
    }

    @Override
    public void launchAuthentication() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
