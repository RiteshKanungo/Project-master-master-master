package com.example.foodbasket.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedProcessData {

    static SharedPreferences sharedPref;
    String PREFS_NAME = "Configuraion";
    static SharedPreferences.Editor editor;

    private static Context context;

    public SharedProcessData(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public static void setString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return sharedPref.getString(key, "Null");
    }

    public static void setStr(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStr(String key) {
        return sharedPref.getString(key, "DNF");
    }

    public static void setBool(String key, boolean value) {
        editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBool(String key) {
        return sharedPref.getBoolean(key, false);
    }
}


