package leonardolana.poppicture.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by leonardolana on 7/27/18.
 * Custom viewpager that allows the customization of the scroller
 */

public class CustomViewPager extends ViewPager {

    private CustomScroller mScroller;

    public CustomViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        try {
            mScroller = new CustomScroller(getContext());
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setScrollDuration(int duration) {
        mScroller.setScrollDuration(duration);
    }

    private static class CustomScroller extends Scroller {

        private int mScrollDuration = 350;

        public CustomScroller(Context context) {
            super(context, new LinearInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        public void setScrollDuration(int duration) {
            mScrollDuration = duration;
        }
    }
}
