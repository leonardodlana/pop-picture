package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;

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
public class ConfirmationDialog extends BaseDialogFragment {

    public static ConfirmationDialog newInstance(@NonNull String title, @NonNull String description,
                                                 String positiveText, String negativeText) {
        ConfirmationDialog fragment = new ConfirmationDialog();
        fragment.setTitle(title);
        fragment.setDescription(description);
        fragment.setPositiveText(positiveText);
        fragment.setNegativeText(negativeText);
        return fragment;
    }

    public interface OnConfirmationDialogListener {
        void onClickPositive();
        void onClickNegative();
    }

    private OnConfirmationDialogListener mOnConfirmationDialogListener;
    private String mTitle;
    private String mDescription;
    private String mPositiveText;
    private String mNegativeText;

    @BindView(R.id.text_title)
    TextView mTextViewTitle;

    @BindView(R.id.text_description)
    TextView mTextViewDescription;

    @BindView(R.id.button_positive)
    TextView mButtonPositive;

    @BindView(R.id.button_negative)
    TextView mButtonNegative;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextViewTitle.setText(mTitle);
        mTextViewDescription.setText(mDescription);
        mButtonNegative.setText(mNegativeText);
        mButtonPositive.setText(mPositiveText);
    }

    @OnClick(R.id.button_positive)
    public void onClickPositive() {
        if(mOnConfirmationDialogListener != null)
            mOnConfirmationDialogListener.onClickPositive();
        dismiss();
    }

    @OnClick(R.id.button_negative)
    public void onClickNegative() {
        if(mOnConfirmationDialogListener != null)
            mOnConfirmationDialogListener.onClickNegative();
        dismiss();
    }

    public void setOnConfirmationDialogListener(OnConfirmationDialogListener onConfirmationDialogListener) {
        mOnConfirmationDialogListener = onConfirmationDialogListener;
    }

    private void setTitle(String title) {
        mTitle = title;
    }

    private void setDescription(String description) {
        mDescription = description;
    }

    private void setNegativeText(String negativeText) {
        mNegativeText = negativeText;
    }

    private void setPositiveText(String positiveText) {
        mPositiveText = positiveText;
    }
}