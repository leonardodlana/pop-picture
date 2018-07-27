package leonardolana.poppicture.common.pictures;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import java.util.List;

import leonardolana.poppicture.data.Picture;

/**
 * Created by leonardolana on 7/27/18.
 */

public class PicturesRecyclerView extends RecyclerView {

    private static final int DEFAULT_SPAN_COUNT = 2;
    private static final int DEFAULT_ORIENTATION = StaggeredGridLayoutManager.VERTICAL;

    private StaggeredGridLayoutManager mLayoutManager;
    private PicturesRecyclerViewAdapter mAdapter;

    public PicturesRecyclerView(Context context) {
        super(context);
        init();
    }

    public PicturesRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PicturesRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLayoutManager = new StaggeredGridLayoutManager(DEFAULT_SPAN_COUNT, DEFAULT_ORIENTATION);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        setLayoutManager(mLayoutManager);

        mAdapter = new PicturesRecyclerViewAdapter();
        setAdapter(mAdapter);
    }


    public void setData(List<Picture> pictureList) {
        mAdapter.setData(pictureList);
    }
}
