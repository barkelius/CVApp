package com.x.swag.swag.util.log;

import android.util.Log;

/**
 * Created by barke on 2016-03-25.
 */
public class Logger {
    public static void error(Class<?> caller, String message){
        error(caller, "", message);
    }

    public static void error(Class<?> caller, String func, String message){
        Log.e(caller.getSimpleName() + "." + func, message);
    }

    public static void log(Class<?> caller, String message){
        log(caller, "", message);
    }

    public static void log(Class<?> caller, String func, String message){
        Log.i(caller.getSimpleName() + "." + func, message);
    }

    static void timing(String message) {
        Log.i("Timing", message);
    }
}
