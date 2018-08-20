package leonardolana.poppicture.editor;

import android.util.Pair;

import java.io.InputStream;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.Utils;
import leonardolana.poppicture.data.Location;
import leonardolana.poppicture.helpers.api.CloudStorage;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.server.RequestError;
import leonardolana.poppicture.server.RequestResponse;
import leonardolana.poppicture.server.ServerRequestAddPicture;

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
public class EditorFragmentPresenter extends BasePresenter {

    private EditorFragmentView mView;
    private UserHelper mUserHelper;
    private ServerHelper mServerHelper;
    private CloudStorage mCloudStorage;

    public EditorFragmentPresenter(EditorFragmentView view, UserHelper userHelper, ServerHelper serverHelper, CloudStorage cloudStorage) {
        mView = view;
        mUserHelper = userHelper;
        mServerHelper = serverHelper;
        mCloudStorage = cloudStorage;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void onClickClose() {
        mView.dismiss();
    }

    public void onClickShare(InputStream originalInputStream, InputStream thumbnailInputStream, final String title, final String description) {
        mView.showLoading();
        final String randomName = Utils.generateSHA256(Utils.randomName() + System.currentTimeMillis());
        String path = mUserHelper.getPublicId() + "/" + randomName + ".jpg";
        String thumbPath = mUserHelper.getPublicId() + "/" + randomName + "_thumb.jpg";

        @SuppressWarnings("unchecked")
        Pair<String, InputStream>[] filesToUpload = new Pair[2];
        filesToUpload[0] = new Pair<>(path, originalInputStream);
        filesToUpload[1] = new Pair<>(thumbPath, thumbnailInputStream);

        mCloudStorage.upload(new CloudStorage.OnUploadListener() {
            @Override
            public void onCompletion() {
                Location location = mUserHelper.getLastKnownLocation();
                new ServerRequestAddPicture(randomName, title, description,
                        location.getLatitude(), location.getLongitude()).execute(mServerHelper, mUserHelper, new RequestResponse() {
                    @Override
                    public void onRequestSuccess(String data) {
                        mView.showSuccess();
                        mView.dismiss();
                        mView.hideLoading();
                    }

                    @Override
                    public void onRequestError(RequestError error) {
                        mView.hideLoading();
                        mView.showError();
                    }
                });
            }

            @Override
            public void onError() {
                mView.hideLoading();
                mView.showError();
            }
        }, filesToUpload);
    }
}
