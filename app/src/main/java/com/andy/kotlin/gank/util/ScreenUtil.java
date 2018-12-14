package com.andy.kotlin.gank.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

/**
 * ScreenUtil
 *
 * @author andyqtchen <br/>
 * 屏幕工具
 * 创建日期：2018/12/13 20:27
 */
public class ScreenUtil {
    private static int sWindowWidth = -1;
    private static int sWindowHeight = -1;

    public static int getWindowScreenWidth(Context context) {
        if (sWindowWidth > 0) {
            return sWindowWidth;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            sWindowWidth = size.x;
        } else {
            sWindowWidth = wm.getDefaultDisplay().getWidth();
        }

        return sWindowWidth;
    }

    public static int getWindowScreenHeight(Context context) {
        if (sWindowHeight > 0) {
            return sWindowHeight;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            sWindowHeight = size.y;
        } else {
            sWindowHeight = wm.getDefaultDisplay().getHeight();
        }

        return sWindowHeight;
    }
}
