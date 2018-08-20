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
public class EditorPictureFragmentPresenter extends BasePresenter {

    private EditorPictureFragmentView mView;

    public EditorPictureFragmentPresenter(EditorPictureFragmentView view) {
        mView = view;

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    void onErrorLoadingFile() {
        mView.showLoadingErrorDialogAndDismiss();
    }

}
