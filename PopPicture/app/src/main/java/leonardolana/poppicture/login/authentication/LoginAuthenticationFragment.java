package leonardolana.poppicture.login.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;

import java.util.List;

import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.RunnableExecutor;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.TrackingHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.RunnableExecutorImpl;
import leonardolana.poppicture.helpers.impl.ServerHelperImpl;
import leonardolana.poppicture.helpers.impl.TrackingHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;

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

public class LoginAuthenticationFragment extends BaseFragment implements LoginAuthenticationView {

    private static final int RC_SIGN_IN = 123;

    private LoginAuthenticationPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context applicationContext = getContext().getApplicationContext();

        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(applicationContext);
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);
        RunnableExecutor runnableExecutor = RunnableExecutorImpl.getInstance();
        ServerHelper serverHelper = ServerHelperImpl.getInstance(applicationContext);
        TrackingHelper trackingHelper = TrackingHelperImpl.getInstance(applicationContext);

        mPresenter = new LoginAuthenticationPresenter(this, runnableExecutor, userHelper,
                serverHelper, trackingHelper);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
            mPresenter.onAuthenticationResult(resultCode);
    }

    /*
        View methods
     */

    @Override
    public void startAuthenticationFlow(List<AuthUI.IdpConfig> providers) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.icon)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void dismiss() {
        getActivity().finish();
    }

    @Override
    public void showError() {
        getActivity().finish();
    }
}
