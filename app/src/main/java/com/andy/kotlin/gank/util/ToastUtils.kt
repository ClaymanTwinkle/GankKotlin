package com.andy.kotlin.gank.util

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * ToastUtils
 * @author andyqtchen <br></br>
 * *         toast
 * *         创建日期：2017/6/7 15:17
 */
object ToastUtils {
    fun toast(context: Context, @StringRes resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    fun toast(context: Context, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(context: Context, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun toastLong(context: Context, @StringRes resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
    }
}
