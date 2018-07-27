package leonardolana.poppicture.home.nearby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.helpers.impl.LocationHelper;

/**
 * Created by leonardolana on 7/24/18.
 */

public class HomeNearbyFragment extends BaseFragment implements HomeNearbyFragmentView {

    private HomeNearbyFragmentPresenter mPresenter;

    @BindView(R.id.loading)
    ProgressBar mProgressBarLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new HomeNearbyFragmentPresenter(this, new LocationHelper(getContext()));
        // It's important to call init with the view model,
        // this way we don't need to handle lifecycle on each fragment
        init(mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_nearby, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
    }
}
