package leonardolana.poppicture.login.authentication;

import com.firebase.ui.auth.AuthUI;

import java.util.List;

/**
 * Created by leonardolana on 7/20/18.
 */

public interface LoginAuthenticationView {

    public void startAuthenticationFlow(List<AuthUI.IdpConfig> providers);

}
