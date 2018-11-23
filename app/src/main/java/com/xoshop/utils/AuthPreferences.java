package com.xoshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xoshop.comm.bean.DemoCache;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class AuthPreferences {
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_APP_TOKEN = "token";
    private static final String KEY_APP_USERINFO = "userinfo";
    private static final String KEY_APP_FIRST = "iffirst";
    private static final String KEY_APP_MUSIC1 = "music1";
    private static final String KEY_APP_MUSIC2 = "music2";
    private static final String KEY_APP_REGID = "regid";
    private static final String KEY_CLIENTID = "clientid";

    public static void saveClientId(String clientId) {
        saveString(KEY_CLIENTID, clientId);
    }

    public static String getClientId() {
        return getString(KEY_CLIENTID);
    }

    public static void setRegId(String regId) {
        saveString(KEY_APP_REGID, regId);
    }

    public static String getRegId() {
        return getString(KEY_APP_REGID);
    }

    public static void setMusic1(String music1) {
        saveString(KEY_APP_MUSIC1, music1);
    }

    public static String getMusic1() {
        return getString(KEY_APP_MUSIC1);
    }

    public static void setMusic2(String music2) {
        saveString(KEY_APP_MUSIC2, music2);
    }

    public static String getMusic2() {
        return getString(KEY_APP_MUSIC2);
    }

    public static void setFirst(String ifFirst) {
        saveString(KEY_APP_FIRST, ifFirst);
    }

    public static String getFirst() {
        return getString(KEY_APP_FIRST);
    }

    public static void saveUserInfo(String userInfo) {
        saveString(KEY_APP_USERINFO, userInfo);
    }

    public static String getUserInfo() {
        return getString(KEY_APP_USERINFO);
    }

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static String getAppToken() {
        return getString(KEY_APP_TOKEN);
    }

    public static void saveAppToken(String token) {
        saveString(KEY_APP_TOKEN, token);
    }

    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return DemoCache.getContext().getSharedPreferences("Demo", Context.MODE_PRIVATE);
    }
}
