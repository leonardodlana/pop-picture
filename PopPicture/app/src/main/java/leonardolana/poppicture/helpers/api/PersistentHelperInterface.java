package leonardolana.poppicture.helpers.api;

/**
 * Created by leonardolana on 7/27/18.
 */

public interface PersistentHelperInterface {

    public void setString(String key, String value);

    public String getString(String key, String defaultValue);

    public void setInt(String key, int value);

    public int getInt(String key, int defaultValue);

    public void setLong(String key, long value);

    public long getLong(String key, long defaultValue);

    public void setFloat(String key, float value);

    public float getFloat(String key, int defaultValue);

    public void setBoolean(String key, boolean value);

    public boolean getBoolean(String key, boolean defaultValue);

}
