package leonardolana.poppicture.login.authentication;

import android.content.Intent;

import com.firebase.ui.auth.AuthUI;

import java.util.List;

import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;

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

    private final LoginAuthenticationPresenter mPresenter;

    public LoginAuthenticationFragment() {
        mPresenter = new LoginAuthenticationPresenter(this);
        init(mPresenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
            mPresenter.onAuthenticationResult(resultCode, data);
    }

    @Override
    public void startAuthenticationFlow(List<AuthUI.IdpConfig> providers) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_launcher_background)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
    }
}
