package com.mimicki.afinal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by DELL on 9/14/2016.
 */
public class SaveSharedPreference {
    static final String PREF_USER_NAME= "username";
    static final String PREF_PASS_WORD = "password";
    static final String PREF_FLAG = "flag";
    private static String PREF_USER_NAME_DRIVER= "usernamedriver";
    private static String PREF_PASS_WORD_DRIVER= "passworddriver";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, String.valueOf(userName));
        editor.commit();
    }

    public static void setPassWord(Context ctx, String passWord) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASS_WORD, String.valueOf(passWord));
        editor.commit();
    }

    public static void setFirstTime(Context ctx, boolean flag){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_FLAG, flag);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getPassWord(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PASS_WORD, "");
    }

    public static Boolean getFirstTime(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_FLAG, true);
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
    /////////////////////////////////////////////////////////////////////

    //////  Driver //////

    public static void setUserNameDriver(Context ctx, String userNameDriver)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME_DRIVER, String.valueOf(userNameDriver));
        editor.commit();
    }

    public static void setPassWordDriver(Context ctx, String passWordDriver) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PASS_WORD_DRIVER, String.valueOf(passWordDriver));
        editor.commit();
    }

    public static String getUserNameDriver(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME_DRIVER, "");
    }

    public static String getPassWordDriver(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PASS_WORD_DRIVER, "");
    }

    public static void clearUserNameDriver(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
