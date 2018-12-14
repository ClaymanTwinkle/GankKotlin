package com.andy.kotlin.gank.image

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.andy.kotlin.gank.util.LoggerRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

/**
 * ImageLoader
 *
 * @author andyqtchen <br></br>
 * 图片加载
 * 创建日期：2018/12/14 09:45
 */
object ImageLoader {

    fun loadImage(imageView: ImageView, url: String?) {
        loadImage(imageView.context, imageView, url)
    }

    fun loadImage(context: Context, imageView: ImageView, url: String?) {
        loadImage(context, imageView, url, SIZE_ORIGINAL, SIZE_ORIGINAL)
    }

    fun loadImage(context: Context, imageView: ImageView, url: String?, requestListener: RequestListener<String, GlideDrawable>) {
        loadImage(context, imageView, url, SIZE_ORIGINAL, SIZE_ORIGINAL, requestListener)
    }

    fun loadImage(imageView: ImageView, url: String?, width: Int, height: Int) {
        loadImage(imageView.context, imageView, url, width, height, LoggerRequestListener())
    }

    fun loadImage(context: Context, imageView: ImageView, url: String?, width: Int, height: Int) {
        loadImage(context, imageView, url, width, height, LoggerRequestListener())
    }

    fun loadImage(context: Context, imageView: ImageView, url: String?, width: Int, height: Int, requestListener: RequestListener<String, GlideDrawable>) {
        Glide.clear(imageView)
        if (TextUtils.isEmpty(url)) return
        Glide.with(context)
                .load(url)
                .override(width, height)
                .listener(requestListener)
                .centerCrop()
                .into(imageView)
    }

    fun pauseRequests(context: Context) {
        Glide.with(context).pauseRequests()
    }

    fun resumeRequests(context: Context) {
        Glide.with(context).resumeRequests()
    }
}