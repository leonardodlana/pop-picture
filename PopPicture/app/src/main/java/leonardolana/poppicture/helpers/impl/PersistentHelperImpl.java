package leonardolana.poppicture.helpers.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import leonardolana.poppicture.helpers.api.PersistentHelper;

/**
 * Created by leonardolana on 7/27/18.
 *
 * This implementation must persist data on android across sessions,
 * since this application is tiny, we can use SharedPreferences{{@link android.content.SharedPreferences}}.
 *
 * If the application needs to persist a lot of data it's better to use a database or other
 * type of persistent storage, because SharedPreference is a single xml file and can be slow
 * to read if becomes huge
 */

public class PersistentHelperImpl implements PersistentHelper {

    private final SharedPreferences mSharedPreferences;

    public PersistentHelperImpl(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setString(String key, String value) {
        changeValue(key, value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void setInt(String key, int value) {
        changeValue(key, value);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public void setLong(String key, long value) {
        changeValue(key, value);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public void setFloat(String key, float value) {
        changeValue(key, value);
    }

    @Override
    public float getFloat(String key, int defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        changeValue(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    private void changeValue(String key, Object value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        if(value instanceof String)
            editor.putString(key, (String) value);
        else if(value instanceof Integer)
            editor.putInt(key, (Integer) value);
        else if(value instanceof Long)
            editor.putLong(key, (Long) value);
        else if(value instanceof Float)
            editor.putFloat(key, (Float) value);
        else if(value instanceof Boolean)
            editor.putBoolean(key, (Boolean) value);
        else
            throw new UnsupportedOperationException(value.getClass().getName() + " is not supported");

        editor.apply();
    }

}
