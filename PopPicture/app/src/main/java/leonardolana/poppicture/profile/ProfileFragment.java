package leonardolana.poppicture.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;

/**
 * Created by leonardolana on 7/25/18.
 */

public class ProfileFragment extends BaseFragment implements ProfileFragmentView {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
