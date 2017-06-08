package com.andy.kotlin.gank.util

import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.util.*

/**

 * LoggerRequestListener
 * @author andyqtchen <br></br>
 * *         Glide logger request listener
 * *         创建日期：2017/6/8 19:47
 */
class LoggerRequestListener : RequestListener<String, GlideDrawable> {
    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
        GLog.e(String.format(Locale.ROOT,
                "Glide:onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e)
        return false
    }

    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
        GLog.d(String.format(Locale.ROOT,
                "Glide:onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource))
        return false
    }
}
