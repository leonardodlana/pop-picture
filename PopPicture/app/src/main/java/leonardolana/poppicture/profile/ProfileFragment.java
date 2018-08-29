package leonardolana.poppicture.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.ServerHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;
import leonardolana.poppicture.login.LoginActivity;
import leonardolana.poppicture.server.RequestError;

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

    @BindView(R.id.image_profile)
    ImageView mImageViewProfile;

    @BindView(R.id.edit_text_profile_name)
    EditText mEditTextProfileName;

    @BindView(R.id.button_profile_update)
    TextView mButtonProfileUpdate;

    @BindView(R.id.button_sign_in)
    TextView mButtonSignIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getContext().getApplicationContext();
        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(applicationContext);
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);
        ServerHelper serverHelper = ServerHelperImpl.getInstance(applicationContext);
        mPresenter = new ProfileFragmentPresenter(this, userHelper, serverHelper);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.button_profile_update, R.id.button_sign_in})
    public void onClickUpdate() {
        mPresenter.onClickUpdate(mEditTextProfileName.getText().toString());
    }

    // View methods

    @Override
    public void setEditEnabled(boolean enabled) {
        mImageViewProfile.setEnabled(enabled);
        mEditTextProfileName.setEnabled(enabled);
        mButtonProfileUpdate.setVisibility(enabled ? View.VISIBLE : View.GONE);
        mButtonSignIn.setVisibility(!enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    public void launchAuthentication() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showUpdateFeedback() {
        Toast.makeText(getContext(), R.string.updated, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdateError(RequestError error) {
        Toast.makeText(getContext(), R.string.error_update_profile, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTextName(String name) {
        mEditTextProfileName.setText(name);
    }
}
