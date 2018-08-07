package leonardolana.poppicture.server;

import android.support.annotation.NonNull;

import com.android.volley.VolleyError;

import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;

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
public class ServerRequestRegister extends ServerRequest implements RequestResponse {

    public interface ServerRequestRegisterResponse {
        void onSuccess();
        void onError(RequestError error);
    }

    private ServerRequestRegisterResponse mCallback;

    public ServerRequestRegister(String firebaseId, String email, String name) {
        super(ServerConstants.URL, "Login.register");
        addParam(KEY_FIREBASE_ID, firebaseId);
        addParam(KEY_EMAIL, email);
        addParam(KEY_NAME, name);
    }

    public void execute(@NonNull ServerHelper serverHelper, @NonNull UserHelper userHelper, @NonNull ServerRequestRegisterResponse callback) {
        mCallback = callback;
        super.execute(serverHelper, userHelper, this);
    }

    @Override
    public void onRequestSuccess(String data) {
        mCallback.onSuccess();
    }

    @Override
    public void onRequestError(RequestError error) {
        mCallback.onError(error);
    }
}
