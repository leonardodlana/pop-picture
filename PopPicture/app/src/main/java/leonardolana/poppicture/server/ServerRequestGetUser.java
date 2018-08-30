package leonardolana.poppicture.server;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import leonardolana.poppicture.helpers.api.RunnableExecutor;
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
public class ServerRequestGetUser extends ServerRequest implements RequestResponse {

    public interface ServerRequestGerUserResponse {
        void onSuccess(String publicId, String userName, String profilePictureName);

        void onError(RequestError e);
    }

    private ServerRequestGerUserResponse mCallback;

    public ServerRequestGetUser(String publicId) {
        super(ServerConstants.URL, "UserData.get");
        addParam(KEY_USER_PUBLIC_ID, publicId);
    }

    public void execute(@NonNull RunnableExecutor runnableExecutor, @NonNull ServerHelper serverHelper, @NonNull UserHelper userHelper,
                        @NonNull ServerRequestGerUserResponse callback) {
        mCallback = callback;
        super.execute(runnableExecutor, serverHelper, userHelper, this);
    }

    @Override
    public void onRequestSuccess(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String publicId = jsonObject.getString(KEY_USER_PUBLIC_ID);
            String name = jsonObject.getString(KEY_NAME);
            String profilePictureName = jsonObject.getString(KEY_PICTURE_NAME);
            mCallback.onSuccess(publicId, name, profilePictureName);
        } catch (Exception e) {
            mCallback.onError(RequestError.JSON_PARSE_ERROR);
        }
    }

    @Override
    public void onRequestError(RequestError error) {
        mCallback.onError(error);
    }
}
