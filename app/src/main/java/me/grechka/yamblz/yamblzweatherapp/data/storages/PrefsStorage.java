package me.grechka.yamblz.yamblzweatherapp.data.storages;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Alexander Dadukin on 21.04.2016.
 */

public final class PrefsStorage implements Storage<String> {

    private static final String TAG = "Weather.App.SharedPrefs";
    private static final String APP_PREFERENCES = "wether.app.shared.preferences";

    private SharedPreferences sharedPreferences;

    public PrefsStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public <T> Storage<String> put(@NonNull String key, @NonNull T value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Double) {
            editor.putString(key, String.valueOf(value));
        }else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            throw new IllegalArgumentException("Argument of provided type cannot be stored in prefs. (Type not supported)");
        }

        Log.d(TAG, "Putted key: " + key + " value: " + value);
        editor.apply();
        return this;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public int getInteger(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void remove(String key) {
        sharedPreferences
                .edit()
                .remove(key)
                .apply();
    }

    @Override
    public void clear() {
        sharedPreferences
                .edit()
                .clear()
                .apply();
    }
}
