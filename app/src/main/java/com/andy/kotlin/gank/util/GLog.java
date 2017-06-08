package com.andy.kotlin.gank.util;

import com.andy.kotlin.gank.BuildConfig;
import com.noveogroup.android.log.Log;

/**
 * GLog
 *
 * @author andyqtchen <br/>
 *         全局Log打印器
 *         创建日期：2017/6/8 19:55
 */
public final class GLog {

    public static void v(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.v(message, throwable);
    }

    public static void d(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.d(message, throwable);
    }

    public static void i(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.i(message, throwable);
    }

    public static void w(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.w(message, throwable);
    }

    public static void e(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.e(message, throwable);
    }

    public static void a(String message, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.a(message, throwable);
    }

    public static void v(Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.v(throwable);
    }

    public static void d(Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.d(throwable);
    }

    public static void i(Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.i(throwable);
    }

    public static void w(Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.w(throwable);
    }

    public static void e(Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.e(throwable);
    }

    public static void a(Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.a(throwable);
    }

    public static void v(Throwable throwable, String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.v(throwable, messageFormat, args);
    }

    public static void d(Throwable throwable, String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.d(throwable, messageFormat, args);
    }

    public static void i(Throwable throwable, String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.i(throwable, messageFormat, args);
    }

    public static void w(Throwable throwable, String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.w(throwable, messageFormat, args);
    }

    public static void e(Throwable throwable, String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.e(throwable, messageFormat, args);
    }

    public static void a(Throwable throwable, String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.a(throwable, messageFormat, args);
    }

    public static void v(String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.v(messageFormat, args);
    }

    public static void d(String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.d(messageFormat, args);
    }

    public static void i(String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.i(messageFormat, args);
    }

    public static void w(String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.w(messageFormat, args);
    }

    public static void e(String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.e(messageFormat, args);
    }

    public static void a(String messageFormat, Object... args) {
        if (BuildConfig.DEBUG)
            Log.a(messageFormat, args);
    }
}
