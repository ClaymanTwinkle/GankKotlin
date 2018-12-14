package com.andy.kotlin.gank.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.activity.LookPictureActivity
import com.andy.kotlin.gank.adapter.CommonAdapter
import com.andy.kotlin.gank.adapter.base.BaseAdapter
import com.andy.kotlin.gank.image.GlideOnRecyclerViewScrollListener
import com.andy.kotlin.gank.image.ImageLoader
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.DensityUtils
import com.andy.kotlin.gank.util.ScreenUtil
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
import kotlinx.android.synthetic.main.fragment_random_gank.*
import kotlinx.android.synthetic.main.list_item_gank.view.*

/**
 * MainFragment

 * @author andyqtchen <br></br>
 * *         MainFragment
 * *         创建日期：2017/6/7 15:29
 */
class GankMeiziFragment : BaseFragment() {

    private var mAdapter: LinearAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_random_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListeners()
        // 首次进入，转个菊花先
        mSwipeRefreshLayout.isRefreshing = true
        loadRandomDataList()
    }

    private fun initData() {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(object : DividerItemDecoration(context, DividerItemDecoration.VERTICAL) {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = DensityUtils.dip2px(view.context, 8F)
                outRect.bottom = DensityUtils.dip2px(view.context, 8F)
                outRect.left = DensityUtils.dip2px(view.context, 16F)
                outRect.right = DensityUtils.dip2px(view.context, 16F)
            }
        })

        mAdapter = LinearAdapter()
        mRecyclerView.adapter = mAdapter
    }

    private fun initListeners() {
        mRecyclerView.addOnScrollListener(GlideOnRecyclerViewScrollListener(context))

        mSwipeRefreshLayout.setOnRefreshListener {
            loadRandomDataList()
        }

        mAdapter!!.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                val model = mAdapter!!.getItem(position)
                LookPictureActivity.startActivity(activity, model.url!!)
            }
        })
    }

    private fun loadRandomDataList() {
        addSubscription(ApiClient.retrofit().loadRandomData("福利", 20), object : ApiCallBack<ApiResponse<List<GankModel>>>(){
            override fun onFailure(code: Int, msg: String?) {
                ToastUtils.toast(context, msg)
            }

            override fun onFinish() {
                mSwipeRefreshLayout.isRefreshing = false
            }

            override fun onSuccess(modelList: ApiResponse<List<GankModel>>) {
                if (modelList.isError) {
                        ToastUtils.toast(context, R.string.server_error)
                } else {
                    if(modelList.results == null){
                        return
                    }
                    mAdapter!!.setAllItemsAndRefresh(modelList.results!!)
                }
            }
        })
    }

    private inner class LinearAdapter : CommonAdapter<GankModel>(R.layout.list_item_meizi) {

        val picWidth:Int
        val picHeight:Int

        init {
            picWidth = ScreenUtil.getWindowScreenWidth(activity) - DensityUtils.dip2px(activity, 20.toFloat())
            picHeight = DensityUtils.dip2px(activity, 100.toFloat())
        }
        override fun bindView(itemView: View, data: GankModel?, position: Int) = with(itemView) {
            if (TextUtils.isEmpty(data!!.url)) {
                ivPic.visibility = View.GONE
            } else {
                ImageLoader.loadImage(ivPic, data.url!!, picWidth, picHeight)
                ivPic.visibility = View.VISIBLE
            }
        }
    }
}
