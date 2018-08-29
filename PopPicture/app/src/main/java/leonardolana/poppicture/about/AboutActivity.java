package leonardolana.poppicture.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseActivity;
import leonardolana.poppicture.common.BasePresenter;

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
public class AboutActivity extends BaseActivity implements AboutActivityView {

    private AboutActivityPresenter mPresenter;

    @BindView(R.id.text_icons)
    TextView mTextIcons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setTitle("About");
        }

        mTextIcons.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected BasePresenter createPresenter() {
        mPresenter = new AboutActivityPresenter(this);
        return mPresenter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.button_review)
    public void onClickReview() {
        mPresenter.onClickReview();
    }

    @OnClick(R.id.button_privacy_policy)
    public void onClickPrivacyPolicy() {
        mPresenter.onClickPrivacyPolicy();
    }

    @OnClick(R.id.button_terms_and_conditions)
    public void onClickTermsAndConditions() {
        mPresenter.onClickTermsAndConditions();
    }

    @OnClick(R.id.button_github)
    public void onClickGitHub() {
        mPresenter.onClickGitHub();
    }

    private void openBrowser(String url) {
        if(TextUtils.isEmpty(url))
            return;

        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception e) {
            // Bad Uri or no browser detected
            Crashlytics.logException(e);
            Toast.makeText(getApplicationContext(), R.string.error_browser_not_found, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /*
        View methods
     */

    @Override
    public void openMarket() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
    }

    @Override
    public void openPrivacyPolicy() {
        openBrowser(getString(R.string.privacy_policy_url));
    }

    @Override
    public void openTermsAndConditions() {
        openBrowser(getString(R.string.terms_and_conditions_url));
    }

    @Override
    public void openGitHub() {
        openBrowser(getString(R.string.github_url));
    }
}
