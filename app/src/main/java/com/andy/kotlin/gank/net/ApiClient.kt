package com.andy.kotlinandroid.net

import com.andy.kotlin.gank.net.HttpClientBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ApiClient
 * @author andyqtchen <br/>
 * 创建日期：2017/6/5 11:24
 */
object ApiClient {
    fun retrofit(): ApiStores {
        val retrofit = Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpClientBuilder.buildOkHttpClient())
                .build()

        return retrofit.create(ApiStores::class.java)
    }
}