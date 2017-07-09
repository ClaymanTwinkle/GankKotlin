package com.andy.kotlin.gank.image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

/**
 * 用于Glide加载图片的OnScrollListener
 * Created by Andy on 2017/7/9.
 */
public class GlideOnScrollListener extends RecyclerView.OnScrollListener {
    private final WeakReference<Context>  mContextWeakReference;

    public GlideOnScrollListener(Context context) {
        mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Context context = mContextWeakReference.get();
        if(context == null) return;
        switch (newState){
            case RecyclerView.SCROLL_STATE_SETTLING:
                Glide.with(context).pauseRequests();
                break;
            default:
                Glide.with(context).resumeRequests();
                break;
        }
    }
}
