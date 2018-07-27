package leonardolana.poppicture.login.authentication;

import android.content.Intent;

import com.firebase.ui.auth.AuthUI;

import java.util.List;

import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseFragment;

/**
 * Created by leonardolana on 7/20/18.
 */

public class LoginAuthenticationFragment extends BaseFragment implements LoginAuthenticationView {

    private static final int RC_SIGN_IN = 123;

    private final LoginAuthenticationPresenter mPresenter;

    public LoginAuthenticationFragment() {
        mPresenter = new LoginAuthenticationPresenter(this);
        init(mPresenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
            mPresenter.onAuthenticationResult(resultCode, data);
    }

    @Override
    public void startAuthenticationFlow(List<AuthUI.IdpConfig> providers) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_launcher_background)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
    }
}
