package com.andy.kotlin.gank.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet

/**
 * RefreshLayout
 * @author andyqtchen <br></br>
 * *         解决SwipeRefreshLayout setRefreshing true不能下拉的问题
 * *         创建日期：2017/6/7 17:48
 */
class RefreshLayout : SwipeRefreshLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun setRefreshing(refreshing: Boolean) {
        if (refreshing) {
            measure(0, 0)
        }
        super.setRefreshing(refreshing)
    }
}
