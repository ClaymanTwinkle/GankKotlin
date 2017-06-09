package com.andy.kotlin.gank.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.event.ApiEvent
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlinandroid.net.ApiClient
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * GankDayFragment

 * @author andyqtchen <br></br>
 * *         天天精品
 * *         创建日期：2017/6/9 10:22
 */
class GankDayFragment : BaseFragment() {
    override fun isRegisterDispatcher(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_day_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        loadDateData()
    }

    private fun loadDateData() {
        addSubscription(ApiClient.retrofit().loadDateData(2017,5,18))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onResponse(event: ApiEvent<ApiResponse<HashMap<String,List<GankModel>>>>) {
        System.err.println(event)
    }
}
