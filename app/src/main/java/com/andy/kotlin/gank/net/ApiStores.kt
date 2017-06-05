package com.andy.kotlinandroid.net

import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * todo 类名
 * @author andyqtchen <br/>
 * todo 实现的主要功能。
 * 创建日期：2017/6/5 11:20
 */
interface ApiStores {
    companion object {
        val API_SERVER_URL = "http://gank.io/api/"
    }

    // 随机数据
    @GET("random/data/{type}/{count}")
    fun loadRandomData(@Path("type") type: String, @Path("count") count: Int): Observable<ApiResponse<GankModel>>
}