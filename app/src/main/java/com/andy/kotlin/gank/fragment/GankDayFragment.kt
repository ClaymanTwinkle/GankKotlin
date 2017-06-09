package com.andy.kotlin.gank.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andy.kotlin.gank.R

/**
 * GankDayFragment

 * @author andyqtchen <br></br>
 * *         天天精品
 * *         创建日期：2017/6/9 10:22
 */
class GankDayFragment : BaseFragment() {
    override fun isRegisterDispatcher(): Boolean {
        return false
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_day_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {

    }


}
