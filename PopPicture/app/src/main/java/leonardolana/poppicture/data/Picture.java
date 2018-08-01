package leonardolana.poppicture.data;

/**
 * Created by leonardolana on 7/24/18.
 */

public class Picture {

    private String mUserId;
    private String mFileName;
    private String mTitle;
    private String mDescription;
    private int mLikesCount;
    private boolean mLikedByMe;
    private double mLatitude;
    private double mLongitude;

    public Picture() {
        //TODO
    }

    public String getUserId() {
        return mUserId;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public boolean isLiked() {
        return mLikedByMe;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

}
