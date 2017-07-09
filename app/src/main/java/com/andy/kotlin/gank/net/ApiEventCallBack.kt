package com.andy.kotlin.gank.net

import com.andy.kotlin.gank.event.ApiEvent
import com.andy.kotlin.gank.util.Dispatcher
import com.andy.kotlinandroid.net.ApiCallBack

/**
 * ApiEventCallBack<M>
 * @author andyqtchen <br></br>
 * *         ApiEventCallBack<M>
 * *         创建日期：2017/6/7 16:20
</M></M> */
class ApiEventCallBack<M> : ApiCallBack<M>() {

    override fun onSuccess(modelList: M) {
        Dispatcher.post(ApiEvent(modelList))
    }

    override fun onFinish() {

    }

    override fun onFailure(code: Int, msg: String?) {
        Dispatcher.post(ApiEvent<Any>(code, msg!!))
    }
}
