package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leonardolana on 7/19/18.
 */

public abstract class BaseFragment extends Fragment {

    private BasePresenter mPresenter;

    public void init(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mPresenter != null)
            mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPresenter != null)
            mPresenter.onSaveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null)
            mPresenter.onDestroy();

        super.onDestroy();
    }

}
