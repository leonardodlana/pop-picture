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
public class AlertDialog extends BaseDialogFragment {

    public static AlertDialog newInstance(@NonNull String title, @NonNull String description) {
        AlertDialog fragment = new AlertDialog();
        fragment.setTitle(title);
        fragment.setDescription(description);
        return fragment;
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    private OnDismissListener mOnDismissListener;
    private String mTitle;
    private String mDescription;

    @BindView(R.id.title)
    TextView mTextViewTitle;

    @BindView(R.id.description)
    TextView mTextViewDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextViewTitle.setText(mTitle);
        mTextViewDescription.setText(mDescription);
    }

    @OnClick(R.id.button_ok)
    public void onClickOK() {
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss();
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    private void setTitle(String title) {
        mTitle = title;
    }

    private void setDescription(String description) {
        mDescription = description;
    }


}
