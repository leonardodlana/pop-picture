package leonardolana.poppicture.editor;

import android.os.Bundle;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.data.TrackingEvent;
import leonardolana.poppicture.helpers.api.ImageLabelHelper;
import leonardolana.poppicture.helpers.api.TrackingHelper;

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
    private TrackingHelper mTrackingHelper;

    public EditorPictureFragmentPresenter(EditorPictureFragmentView view, TrackingHelper trackingHelper) {
        mView = view;
        mTrackingHelper = trackingHelper;
    }

    @Override
    public void onDestroy() {
        mView = null;
        mTrackingHelper = null;
    }

    void onErrorLoadingFile() {
        mView.showLoadingErrorDialogAndDismiss();
    }

    public void onMatureContentResponse(ImageLabelHelper.MatureContent matureContent) {
        if(matureContent != ImageLabelHelper.MatureContent.NONE) {
            mView.showMatureContentWarning(matureContent);
        }

        Bundle params = new Bundle();
        params.putString(TrackingHelper.PARAM_CONTENT, matureContent.name());
        mTrackingHelper.log(TrackingEvent.EDITOR_MATURE_CONTENT, params);
    }
}
