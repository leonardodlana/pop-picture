package leonardolana.poppicture.common;

import android.support.v4.view.ViewPager;

/**
 * Created by leonardolana on 7/26/18.
 * This is a good way to wrap an interface so we only
 * have to implement the methods that we want. It's usually
 * good for big interfaces of android
 */

public abstract class AbstractOnPageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
