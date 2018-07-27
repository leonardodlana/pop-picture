package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by leonardolana on 7/19/18.
 */

public abstract class BasePresenter {

    /**
     * On screen create
     * @param savedInstanceState
     */
    public void onCreate(@Nullable Bundle savedInstanceState) {}

    /**
     * On screen resume
     */
    public void onResume() {}

    /**
     * On screen pause
     */
    public void onPause() {}

    /**
     * On save state, called before the destroy, usually
     * when the system needs memory
     * @param outState
     */

    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * On screen destroy
     */
    public abstract void onDestroy();

}
