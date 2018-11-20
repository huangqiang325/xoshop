package com.kulian.comm.bean;

import android.app.Notification;
import android.content.Context;
import android.util.SparseArray;

public class DemoCache {

    private static Context context;

    private static String account;
    private static String nickname;

    private static SparseArray<Notification> notifications = new SparseArray<>();

    public static SparseArray<Notification> getNotifications() {
        return notifications;
    }

    public static void clear() {
        account = null;
    }

    public static String getAccount() {
        return account;
    }

    public static String getNickname() {
        return nickname;
    }

    private static boolean mainTaskLaunching;

    public static void setAccount(String account) {
        DemoCache.account = account;
        //NimUIKit.setAccount(account);
    }



    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DemoCache.context = context.getApplicationContext();
    }

    public static void setMainTaskLaunching(boolean mainTaskLaunching) {
        DemoCache.mainTaskLaunching = mainTaskLaunching;
    }

    public static boolean isMainTaskLaunching() {
        return mainTaskLaunching;
    }
}
