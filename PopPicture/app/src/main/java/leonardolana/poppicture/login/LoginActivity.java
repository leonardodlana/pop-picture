package leonardolana.poppicture.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseActivity;
import leonardolana.poppicture.login.authentication.LoginAuthenticationFragment;

/**
 * Created by leonardolana on 7/20/18.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if(savedInstanceState == null) {
            addFragment(new LoginAuthenticationFragment(), R.id.fragment_container, false);
        }
    }

}
