package com.andy.kotlinandroid.net

import com.andy.kotlin.gank.BuildConfig
import com.andy.kotlin.gank.GankApplication.Companion.context
import com.andy.kotlin.gank.util.AppUtil
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * ApiClient
 * @author andyqtchen <br/>
 * 创建日期：2017/6/5 11:24
 */
object ApiClient {
    fun retrofit(): ApiStores {
        val okHttpClient = buildOkHttpClient()
        val retrofit = Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiStores::class.java)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val cacheFile = File(context?.cacheDir, "http")
        val cache = Cache(cacheFile, 1024 * 1024 * 10) // 10Mb

        val builder = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(CacheInterceptor())

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var request = chain!!.request() //拦截reqeust
            if (!AppUtil.isNetworkReachable(context)) {//判断网络连接状况
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            val response = chain.proceed(request)
            if (AppUtil.isNetworkReachable(context)) {
                val maxAge = 60 * 60 // 有网络时 设置缓存超时时间1个小时
                response.newBuilder()
                        .removeHeader("Pragma") //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", "public, max-age=" + maxAge) //设置缓存超时时间
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // 无网络时，设置超时为4周
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)//设置缓存策略，及超时策略
                        .build()
            }
            return response
        }
    }
}