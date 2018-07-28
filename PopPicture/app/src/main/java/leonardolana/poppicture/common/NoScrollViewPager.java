package leonardolana.poppicture.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by leonardolana on 7/26/18.
 */

public class NoScrollViewPager extends CustomViewPager {

    private boolean mIsScrollEnabled = false;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mIsScrollEnabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mIsScrollEnabled && super.onInterceptTouchEvent(ev);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        mIsScrollEnabled = scrollEnabled;
    }
}
