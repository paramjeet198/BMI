package com.techbull.bmi.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PreferenceHelper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        editor = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit();
    }

    public void saveToPref(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void saveToPref(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public void saveToPref(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public void saveToPref(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }


    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    public float getFloat(String key, float def) {
        return preferences.getFloat(key, def);
    }

    public boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

}
