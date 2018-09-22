package leonardolana.poppicture.common.picture;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo Lana
 * Github: https://github.com/leonardodlana
 * <p>
 * Copyright 2018 Leonardo Lana
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class PictureRecyclerView extends RecyclerView {

    public interface OnScrollListener {
        void onScrolled(int dx, int dy);
    }

    private static final int DEFAULT_SPAN_COUNT = 2;
    private static final int DEFAULT_ORIENTATION = StaggeredGridLayoutManager.VERTICAL;

    private StaggeredGridLayoutManager mLayoutManager;
    private List<OnScrollListener> mOnScrollListeners = new ArrayList<>();

    public PictureRecyclerView(Context context) {
        super(context);
        init();
    }

    public PictureRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PictureRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLayoutManager = new StaggeredGridLayoutManager(DEFAULT_SPAN_COUNT, DEFAULT_ORIENTATION);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        setLayoutManager(mLayoutManager);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        for(OnScrollListener onScrolledListener : mOnScrollListeners) {
            onScrolledListener.onScrolled(dx, dy);
        }
    }

    public void setColumnCount(int count) {
        mLayoutManager.setSpanCount(count);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListeners.add(onScrollListener);
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListeners.remove(onScrollListener);
    }
}
