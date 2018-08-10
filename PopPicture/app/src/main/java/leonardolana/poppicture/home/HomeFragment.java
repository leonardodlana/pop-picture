package leonardolana.poppicture.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.data.HomeSection;
import leonardolana.poppicture.editor.EditorFragment;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;
import leonardolana.poppicture.onboarding.OnboardingFragment;

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

    @BindView(R.id.fragment_viewpager)
    ViewPager mViewPagerContainer;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.add_media_fab)
    FloatingActionButton mAddMediaFab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(getContext().getApplicationContext());
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);

        mPresenter = new HomeFragmentPresenter(this, persistentHelper, userHelper);
        // It's important to call init with the view model,
        // this way we don't need to handle lifecycle on each fragment
        init(mPresenter);
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


        if (savedInstanceState != null) {
            /*
                If the instance is saved, we can restore the fragments
             */
            mPageAdapter = new HomeFragmentPagerAdapter(getFragmentManager());
            mPageAdapter.restoreFragments(savedInstanceState);
        }

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
    public void onSaveInstanceState(Bundle outState) {
        mPageAdapter.saveFragments(outState);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.add_media_fab)
    public void onFabClick() {
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
        if(visible && mAddMediaFab.getVisibility() == View.VISIBLE)
            return;

        if(!visible && mAddMediaFab.getVisibility() == View.GONE)
            return;

        float fromY, toY;

        int bottomMargin = ((ViewGroup.MarginLayoutParams) mAddMediaFab.getLayoutParams()).bottomMargin;

        int translationY = (mAddMediaFab.getBottom() - mAddMediaFab.getTop()) + bottomMargin;

        if(!visible) {
            fromY = 0;
            toY = translationY;
        } else {
            fromY = translationY;
            toY = 0;
        }

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, fromY, toY);
        translateAnimation.setDuration(400);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(visible) {
                    mAddMediaFab.setVisibility(View.VISIBLE);
                    mAddMediaFab.setClickable(true);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!visible) {
                    mAddMediaFab.setVisibility(View.GONE);
                    mAddMediaFab.setClickable(false);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAddMediaFab.startAnimation(translateAnimation);
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

        if(mViewPagerContainer.getCurrentItem() != itemPosition) {
            mViewPagerContainer.setCurrentItem(itemPosition, true);
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
        intent.putExtra(Intent.EXTRA_MIME_TYPES, allowedMimeTypes);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
    }

    @Override
    public void openProfileAndLogin() {
        onClickSection(HomeSection.PROFILE);
    }
}
