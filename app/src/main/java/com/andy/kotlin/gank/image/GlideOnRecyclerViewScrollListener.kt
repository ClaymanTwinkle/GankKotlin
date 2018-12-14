package com.andy.kotlin.gank.image

import android.content.Context
import android.support.v7.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * 用于Glide加载图片的OnScrollListener
 * Created by Andy on 2017/7/9.
 */
class GlideOnRecyclerViewScrollListener(context: Context) : RecyclerView.OnScrollListener() {
    private val mContextWeakReference: WeakReference<Context>
    private var mIsPauseRequests = false

    init {
        mContextWeakReference = WeakReference(context)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val context = mContextWeakReference.get() ?: return
        when (newState) {
            RecyclerView.SCROLL_STATE_SETTLING -> {
                mIsPauseRequests = true
                ImageLoader.pauseRequests(context)
            }
            else -> if (mIsPauseRequests) {
                mIsPauseRequests = false
                ImageLoader.resumeRequests(context)
            }
        }
    }
}
