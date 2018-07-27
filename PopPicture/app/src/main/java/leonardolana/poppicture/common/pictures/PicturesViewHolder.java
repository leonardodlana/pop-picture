package leonardolana.poppicture.common.pictures;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;

/**
 * Created by leonardolana on 7/27/18.
 */

public class PicturesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    ImageView mImageView;

    public PicturesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setImage(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    public void setImage(int drawableId) {
        mImageView.setImageResource(drawableId);
    }

}
