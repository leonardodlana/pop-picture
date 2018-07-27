package leonardolana.poppicture.common.pictures;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import leonardolana.poppicture.R;
import leonardolana.poppicture.data.Picture;

/**
 * Created by leonardolana on 7/27/18.
 */

public class PicturesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SMALL = 0;
    private static final int VIEW_TYPE_MEDIUM = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private final List<Pair<Integer, Picture>> mData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;

        switch (viewType) {
            case VIEW_TYPE_SMALL:
                layout = R.layout.picture_small_view;
                break;
            case VIEW_TYPE_MEDIUM:
                layout = R.layout.picture_medium_view;
                break;
            case VIEW_TYPE_BIG:
                layout = R.layout.picture_big_view;
                break;
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        return new PicturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int position) {
        PicturesViewHolder holder = (PicturesViewHolder) h;
        holder.setImage(R.drawable.place_holder);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).first;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Picture> pictureList) {
        mData.clear();
        designateLayout(pictureList);
        notifyDataSetChanged();
    }

    private void designateLayout(List<Picture> pictures) {
        Random rnd = new Random();

        for(Picture picture : pictures) {
            mData.add(new Pair<Integer, Picture>(rnd.nextInt(3), picture));
        }
    }
}
