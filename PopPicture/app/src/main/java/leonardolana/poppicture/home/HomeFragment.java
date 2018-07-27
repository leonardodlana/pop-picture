package leonardolana.poppicture.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.AbstractOnPageChangeListener;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.data.HomeSection;
import leonardolana.poppicture.helpers.impl.PersistentHelper;
import leonardolana.poppicture.onboarding.OnboardingFragment;


public class HomeFragment extends BaseFragment implements HomeFragmentView {

    private HomeFragmentPresenter mPresenter;

    private HomeFragmentPagerAdapter mPageAdapter;

    @BindView(R.id.fragment_viewpager)
    ViewPager mViewPagerContainer;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new HomeFragmentPresenter(this, new PersistentHelper(getContext()));
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


        if(savedInstanceState != null) {
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

    // >>>>>>> View methods

    /**
     * This method is called if it's a new instance, otherwise the fragments are restored
     * @param homeSections
     */
    @Override
    public void setSections(HomeSection[] homeSections) {
        if(mViewPagerContainer != null) {
            mPageAdapter = new HomeFragmentPagerAdapter(getFragmentManager(), homeSections);
            mViewPagerContainer.setAdapter(mPageAdapter);
        }
    }

    @Override
    public void onClickSection(HomeSection homeSection) {
        int itemPosition = mPageAdapter.getItemPosition(homeSection);
        mViewPagerContainer.setCurrentItem(itemPosition, true);
    }

    @Override
    public void showOnboarding() {
        OnboardingFragment onboardingFragment = new OnboardingFragment();
        onboardingFragment.show(getFragmentManager(), "dialog");
    }
}
