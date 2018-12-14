package com.andy.kotlin.gank.image;

import android.content.Context;

import com.andy.kotlin.gank.net.HttpClientBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;

/**
 * ImageHttpModule
 *
 * @author andyqtchen <br/>
 * 图片 http请求 模块t
 * 创建日期：2018/12/14 15:37
 */
public class ImageHttpModule extends OkHttpGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpClientBuilder.INSTANCE.buildOkHttpClient()));
    }
}
