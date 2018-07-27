package leonardolana.poppicture.common;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import leonardolana.poppicture.helpers.PermissionHelper;

/**
 * Created by leonardolana on 7/20/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void addFragment(Fragment frag, int container, boolean addToBackStack) {
        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(container);

        FragmentTransaction ft = fm.beginTransaction();

        if (fragment != null && addToBackStack)
            ft.addToBackStack(fragment.getClass().getName());

        ft.replace(container, frag, frag.getClass().getName());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
