package com.andy.kotlin.gank.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Andy on 2017/7/9.
 */

public final class ActivityUtil {
    public static void startActivity(Activity activity, Class<? extends Activity> clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    public static void startActivity(Fragment fragment, Class<? extends Activity> clazz) {
        Intent intent = new Intent(fragment.getContext(), clazz);
        fragment.startActivity(intent);
    }
}
