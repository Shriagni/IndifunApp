package com.deificindia.indifun1.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class MySharePref {

    public static final String USERID = "userId";
    public static final String PHONE1 = "mob";
    public static final String ISOTPVARIFIED = "ISOTPVARIFIED";

    public static SharedPreferences sp;

    public static void saveData(Context context, String key, String value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserId(){
        return IndifunApplication.getInstance().getCredential().getId();
    }

    public static String getData(Context context, String key, String value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        return sp.getString(key, value);
    }


    public static void saveIntData(Context context, String key, int value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntData(Context context, String key, int value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    public static void saveBoolData(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolData(Context context, String key, boolean value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static void deleteData(Context context) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void NullData(Context context, String key) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, null);
        editor.commit();
    }

}
