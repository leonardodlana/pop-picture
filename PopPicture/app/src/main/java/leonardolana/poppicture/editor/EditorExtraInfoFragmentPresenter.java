package leonardolana.poppicture.editor;

import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.EditFieldError;

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
public class EditorExtraInfoFragmentPresenter extends BasePresenter {

    private EditorExtraInfoFragmentView mView;

    public EditorExtraInfoFragmentPresenter(EditorExtraInfoFragmentView view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    // TODO maybe get this from elsewhere
    public int getDescriptionMinimumSize() {
        return 30;
    }

    // TODO maybe get this from elsewhere
    public int getTitleMinimumSize() {
        return 5;
    }

    public boolean validateFields(String title, String description) {
        EditFieldError titleError = validateTitle(title);
        EditFieldError descriptionError = validateDescription(description);

        return titleError == null && descriptionError == null;
    }

    private EditFieldError validateTitle(String title) {
        EditFieldError error = null;

        if (title == null || title.length() == 0) {
            error = EditFieldError.EMPTY;
        } else if (title.length() < getTitleMinimumSize()) {
            error = EditFieldError.NOT_ENOUGH;
        }

        if (error != null) {
            mView.showFieldTitleError(error);
        }

        return error;
    }

    private EditFieldError validateDescription(String description) {
        EditFieldError error = null;

        if (description == null || description.length() == 0) {
            error = EditFieldError.EMPTY;
        } else if (description.length() < getDescriptionMinimumSize()) {
            error = EditFieldError.NOT_ENOUGH;
        }

        if (error != null) {
            mView.showFieldDescriptionError(error);
        }

        return error;
    }
}
