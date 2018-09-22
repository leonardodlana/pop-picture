package leonardolana.poppicture.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.about.AboutActivity;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.PicturesChangeBroadcast;
import leonardolana.poppicture.data.HomeSection;
import leonardolana.poppicture.data.PersistentSharedKeys;
import leonardolana.poppicture.editor.EditorFragment;
import leonardolana.poppicture.editor.EditorPictureFragment;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.ServerHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;
import leonardolana.poppicture.onboarding.OnboardingFragment;
import leonardolana.poppicture.profile.ProfileOnboardingDialogFragment;

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

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    private static final int REQUEST_CODE_FILE_PICKER = 5050;

    private HomeFragmentPresenter mPresenter;
    private HomeFragmentPagerAdapter mPageAdapter;
    private PicturesChangeBroadcast mOnPicturesChangeBroadcast;
    private TranslateAnimation mFabTranslationAnimation;

    @BindView(R.id.button_info)
    AppCompatImageView mButtonInfo;

    @BindView(R.id.fragment_viewpager)
    ViewPager mViewPagerContainer;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.add_media_fab)
    FloatingActionButton mAddMediaFab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getContext().getApplicationContext();

        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(applicationContext);
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);

        mPresenter = new HomeFragmentPresenter(this, persistentHelper, userHelper);
        mOnPicturesChangeBroadcast = new PicturesChangeBroadcast() {
            @Override
            protected void onPicturesScrolled(int dx, int dy) {
                mPresenter.onPicturesScrolled(dx, dy);
            }
        };
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOnPicturesChangeBroadcast.register(getContext());

        mViewPagerContainer.setOffscreenPageLimit(3);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mPresenter.onClickMenu(getResources().getResourceEntryName(item.getItemId()));
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        mOnPicturesChangeBroadcast.unRegister(getContext());
        super.onDestroyView();
    }

    @OnClick(R.id.button_info)
    public void onClickInfo() {
        mPresenter.onClickInfo();
    }

    @OnClick(R.id.add_media_fab)
    public void onClickFab() {
        mPresenter.onClickFab();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    EditorFragment editorFragment = EditorFragment.newInstance(data.getData());
                    editorFragment.show(getFragmentManager(), "dialog");
                }
            }
        }
    }

    private void setFabVisibility(final boolean visible) {
        if(mFabTranslationAnimation != null)
            return;

        if (visible && mAddMediaFab.getVisibility() == View.VISIBLE)
            return;

        if (!visible && mAddMediaFab.getVisibility() == View.GONE)
            return;

        float fromY, toY;

        int bottomMargin = ((ViewGroup.MarginLayoutParams) mAddMediaFab.getLayoutParams()).bottomMargin;

        int translationY = (mAddMediaFab.getBottom() - mAddMediaFab.getTop()) + bottomMargin;

        if (!visible) {
            fromY = 0;
            toY = translationY;
        } else {
            fromY = translationY;
            toY = 0;
        }

        mFabTranslationAnimation = new TranslateAnimation(0, 0, fromY, toY);
        mFabTranslationAnimation.setDuration(400);
        mFabTranslationAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mFabTranslationAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visible) {
                    mAddMediaFab.setVisibility(View.VISIBLE);
                    mAddMediaFab.setClickable(true);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!visible) {
                    mAddMediaFab.setVisibility(View.GONE);
                    mAddMediaFab.setClickable(false);
                }
                mFabTranslationAnimation = null;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAddMediaFab.startAnimation(mFabTranslationAnimation);
    }

    // >>>>>>> View methods

    /**
     * This method is called if it's a new instance, otherwise the fragments are restored
     *
     * @param homeSections
     */
    @Override
    public void setSections(HomeSection[] homeSections) {
        if (mViewPagerContainer != null) {
            mPageAdapter = new HomeFragmentPagerAdapter(getFragmentManager(), homeSections);
            mViewPagerContainer.setAdapter(mPageAdapter);
        }
    }

    @Override
    public void onClickSection(HomeSection homeSection) {
        int itemPosition = mPageAdapter.getItemPosition(homeSection);

        if (mViewPagerContainer.getCurrentItem() != itemPosition) {
            mViewPagerContainer.setCurrentItem(itemPosition, true);
            // We could add the android id to the HomeSection item, but, by doing so, the homesection
            // would know android classes, which is not the desired outcome
            mBottomNavigationView.setSelectedItemId(getResources().getIdentifier(homeSection.getMenu(), "id", getContext().getPackageName()));
            setFabVisibility(homeSection != HomeSection.PROFILE);
        }
    }

    @Override
    public void showOnboarding() {
        OnboardingFragment onboardingFragment = new OnboardingFragment();
        onboardingFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void openFilePicker(String allowedMimeTypes) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(allowedMimeTypes);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
    }

    @Override
    public void openProfile() {
        if (PersistentSharedKeys.needToShowProfileOnboarding(PersistentHelperImpl.getInstance(getContext().getApplicationContext()))) {
            ProfileOnboardingDialogFragment onboardingFragment = new ProfileOnboardingDialogFragment();
            onboardingFragment.show(getFragmentManager(), "dialog");
        }

        onClickSection(HomeSection.PROFILE);
    }

    @Override
    public void openAbout() {
        Intent intent = new Intent(getContext(), AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFab() {
        setFabVisibility(true);
    }

    @Override
    public void hideFab() {
        setFabVisibility(false);
    }
}
