package leonardolana.poppicture.editor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.EditFieldError;
import leonardolana.poppicture.editor.contract.EditorExtraInfoContract;

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
public class EditorExtraInfoFragment extends BaseFragment implements EditorExtraInfoFragmentView, EditorExtraInfoContract {

    private EditorExtraInfoFragmentPresenter mPresenter;

    @BindView(R.id.edit_title)
    AppCompatEditText mEditTitle;

    @BindView(R.id.edit_description)
    AppCompatEditText mEditDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EditorExtraInfoFragmentPresenter(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor_extra_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                areFieldsValid();
            }
        };

        mEditTitle.addTextChangedListener(textWatcher);
        mEditDescription.addTextChangedListener(textWatcher);
        areFieldsValid();
    }

    /*
        View methods
     */

    @Override
    public void showFieldTitleError(EditFieldError error) {
        switch (error) {
            case EMPTY:
                mEditTitle.setError(getString(R.string.error_string_empty));
                break;
            case NOT_ENOUGH:
                mEditTitle.setError(String.format(getString(R.string.error_string_not_enough),
                        mPresenter.getTitleMinimumSize()));
                break;
        }
    }

    @Override
    public void showFieldDescriptionError(EditFieldError error) {
        switch (error) {
            case EMPTY:
                mEditDescription.setError(getString(R.string.error_string_empty));
                break;
            case NOT_ENOUGH:
                mEditDescription.setError(String.format(getString(R.string.error_string_not_enough),
                        mPresenter.getDescriptionMinimumSize()));
                break;
        }
    }

    /*
        Contract methods
     */

    @Override
    public boolean areFieldsValid() {
        return mPresenter.validateFields(getTitle(), getDescription());
    }

    @Override
    public String getTitle() {
        return mEditTitle.getText().toString();
    }

    @Override
    public String getDescription() {
        return mEditDescription.getText().toString();
    }
}
