package com.andy.kotlinandroid.net

import android.util.Log
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber

/**
 * todo 类名
 * @author andyqtchen <br/>
 * todo 实现的主要功能。
 * 创建日期：2017/6/5 11:34
 */
abstract class ApiCallBack<M> : Subscriber<M>() {
    abstract fun onSuccess(model: M)
    abstract fun onFailure(msg: String?)
    abstract fun onFinish()

    override fun onCompleted() {
        onFinish()
    }

    override fun onNext(t: M) {
        onSuccess(t)
    }

    override fun onError(e: Throwable?) {
        if (e is HttpException) {
            val httpException = e
            val code = httpException.code()
            var msg = httpException.message
            Log.d("andy", "code = "+code)
            if(code == 554) {
                msg = "网络不给力"
            } else if(code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(e.toString())
        }
        onFinish()
    }
}