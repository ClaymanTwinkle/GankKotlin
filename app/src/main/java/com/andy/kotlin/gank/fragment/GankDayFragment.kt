package com.andy.kotlin.gank.fragment

import android.os.Bundle
import com.andy.kotlin.gank.util.DateUtil
import com.andy.kotlinandroid.net.ApiClient
import java.util.*

/**
 * 一天的gank 推荐
 * Created by Andy on 2017/7/9.
 */
class GankDayFragment : GankHomeFragment() {
    private var mCurrentDate:Date? = null
    companion object {
        private val KEY_TIME = "key_time"

        fun newInstance(time: Date): GankDayFragment {
            val args = Bundle()
            args.putSerializable(KEY_TIME, time)
            val fragment = GankDayFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData(){
        mCurrentDate = arguments[KEY_TIME] as Date?
        super.initData()
    }

    override fun loadDateData() {
        val year = DateUtil.getYear(mCurrentDate)
        val month = DateUtil.getMonth(mCurrentDate)
        val day = DateUtil.getDay(mCurrentDate)
        addSubscription(ApiClient.retrofit().loadDateData(year, month, day), LoadDataCallBack())
    }
}
