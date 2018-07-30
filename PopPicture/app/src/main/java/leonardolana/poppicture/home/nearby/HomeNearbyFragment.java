package leonardolana.poppicture.home.nearby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.picture.PictureRecyclerView;
import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.helpers.mock.PicturesLoaderHelperMock;

/**
 * Created by leonardolana on 7/24/18.
 */

public class HomeNearbyFragment extends BaseFragment implements HomeNearbyFragmentView {

    private HomeNearbyFragmentPresenter mPresenter;

    @BindView(R.id.loading)
    ProgressBar mProgressBarLoading;

    @BindView(R.id.pictures_recycler_view)
    PictureRecyclerView mPicturesRecyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new HomeNearbyFragmentPresenter(this, new PicturesLoaderHelperMock());
        // It's important to call init with the view model,
        // this way we don't need to handle lifecycle on each fragment
        init(mPresenter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_nearby, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void showLoading() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoad(List<Picture> pictures) {
        mPicturesRecyclerView.setData(pictures);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadError() {

    }
}
