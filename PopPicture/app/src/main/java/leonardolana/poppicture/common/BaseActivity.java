package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import leonardolana.poppicture.helpers.PermissionHelper;

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

public abstract class BaseActivity extends AppCompatActivity {

    private BasePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();

        if (mPresenter != null)
            mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mPresenter != null)
            mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        super.onDestroy();
    }

    protected abstract BasePresenter createPresenter();

    protected void addFragment(Fragment frag, int container, boolean addToBackStack) {
        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(container);

        FragmentTransaction ft = fm.beginTransaction();

        if (fragment != null && addToBackStack)
            ft.addToBackStack(fragment.getClass().getName());

        ft.replace(container, frag, frag.getClass().getName());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
