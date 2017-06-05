package com.andy.kotlin.gank.util

import android.content.Context

/**
 * DensityUtils

 * @author andyqtchen <br></br>
 * *         创建日期：2017/6/5 17:29
 */
object DensityUtils {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * @param context
     * *
     * @param pxValue
     * *
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * @param context
     * *
     * @param dipValue
     * *
     * @return
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param context
     * *
     * @param pxValue
     * *
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param context
     * *
     * @param spValue
     * *
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}
