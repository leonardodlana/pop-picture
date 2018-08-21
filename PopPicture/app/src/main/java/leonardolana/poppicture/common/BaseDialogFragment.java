package leonardolana.poppicture.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
 *
 *
 * A base dialog fragment that handles the life cycles and has some ui
 * resources
 *
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private BasePresenter mPresenter;
    private boolean mIsFullScreen = false;
    private boolean mHasTitle = true;
    private LoadingDialog mLoadingDialog;

    public void init(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = new LoadingDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog;
        if (mIsFullScreen) {
            dialog = new AppCompatDialog(getActivity(), R.style.FullScreenDialog);
        } else
            dialog = new AppCompatDialog(getActivity(), 0);

        if (mIsFullScreen) {
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        }
        if(!mHasTitle)
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter != null)
            mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPresenter != null)
            mPresenter.onSaveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();

        super.onDestroy();
    }

    protected void setHasTitle(boolean hasTitle) {
        mHasTitle = hasTitle;
    }

    protected void setFullScreen(boolean isFullScreen) {
        mIsFullScreen = isFullScreen;
    }

    public void showLoading() {
        mLoadingDialog.show(getFragmentManager(), "dialog");
    }

    public void hideLoading() {
        mLoadingDialog.dismiss();
    }
}
