package com.winux.languagepack.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DataProccessor {

    public static final String KEY_STORE_COMPLETE_JSON = "JSON_DATA";
    public static final String KEY_STORE_FETCHING_DATA_URL = "DATA_URL";
    public static final String KEY_STORE_UPDATED_AT = "updated_at";
    public static final String KEY_STORE_UPDATE_TIME_INTERVAL = "time_interval";
    public static final String KEY_REMEMBER_LAST_LOCALE = "last_known_locale";

    private Context context;
    private static DataProccessor instance;

    private DataProccessor() {
    }

    public static synchronized DataProccessor getInstance(Context context) {
        if (instance == null) {
            instance = new DataProccessor();
        }
        instance.context = context;
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public final static String PREFS_NAME = "winux_lang_pack";

    public void setInt(String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(key, 0);
    }

    public void setString(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(key, null);
    }

    public void setLong(String key, long value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getLong(key, 0);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(key, false);
    }
}
