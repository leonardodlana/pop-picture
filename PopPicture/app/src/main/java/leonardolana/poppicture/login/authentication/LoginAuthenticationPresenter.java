package leonardolana.poppicture.login.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.server.RequestError;
import leonardolana.poppicture.server.ServerRequestRegister;
import leonardolana.poppicture.server.RequestResponse;

import static android.app.Activity.RESULT_OK;

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
public class LoginAuthenticationPresenter extends BasePresenter {

    private ServerHelper mServerHelper;
    private UserHelper mUserHelper;
    private LoginAuthenticationView mView;

    public LoginAuthenticationPresenter(LoginAuthenticationView view, UserHelper userHelper, ServerHelper serverHelper) {
        mView = view;
        mUserHelper = userHelper;
        mServerHelper = serverHelper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        mView.startAuthenticationFlow(providers);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void onAuthenticationResult(int resultCode) {

        if (resultCode == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mUserHelper.setFirebaseId(user.getUid());
            mUserHelper.setName(user.getDisplayName());
            mUserHelper.setEmail(user.getEmail());
            new ServerRequestRegister(user.getUid(),
                    user.getEmail(),
                    user.getDisplayName())
                    .execute(mServerHelper, mUserHelper, new ServerRequestRegister.ServerRequestRegisterResponse() {
                        @Override
                        public void onSuccess() {
                            mView.dismiss();
                        }

                        @Override
                        public void onError(RequestError error) {

                        }
                    });
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button_contained_shape. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}
