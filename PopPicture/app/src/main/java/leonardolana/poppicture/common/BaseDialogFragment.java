package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * Created by leonardolana on 7/26/18.
 */

public abstract class BaseDialogFragment extends DialogFragment {

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
