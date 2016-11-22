package com.x.swag.swag.util.log;

/**
 * Created by barke on 2016-03-25.
 */
public class Logger {
    public static void error(Class<?> caller, String message){
        error(caller, "", message);
    }

    public static void error(Class<?> caller, String func, String message){
        System.err.println(caller.getSimpleName() + "." + func + "(...): " + message);
    }

    public static void log(Class<?> caller, String message){
        log(caller, "", message);
    }

    public static void log(Class<?> caller, String func, String message){
        System.out.println(caller.getSimpleName() + "." + func + "(...): " + message);
    }

}
