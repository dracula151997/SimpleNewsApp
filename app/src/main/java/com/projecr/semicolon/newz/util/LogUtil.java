package com.projecr.semicolon.newz.util;

import android.util.Log;

public class LogUtil {
    public static final String TAG = LogUtil.class.getSimpleName();

    public static void verbose(String messsage) {
        Log.v(TAG, messsage);
    }

    public static void verbose(String message, Exception e) {
        Log.v(TAG, message, e);
    }

    public static void error(String message) {
        Log.e(TAG, message);
    }

    public static void error(String message, Exception e) {
        Log.e(TAG, message, e);
    }
}
