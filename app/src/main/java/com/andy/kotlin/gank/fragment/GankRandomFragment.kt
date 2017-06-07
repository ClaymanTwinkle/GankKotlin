package com.andy.kotlin.gank.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.WebBrowserActivity
import com.andy.kotlin.gank.adapter.BaseAdapter
import com.andy.kotlin.gank.adapter.BaseViewHolder
import com.andy.kotlin.gank.adapter.ListBaseAdapter
import com.andy.kotlin.gank.event.ApiEvent
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.DensityUtils
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiClient
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_random_gank.*
import kotlinx.android.synthetic.main.list_item_gank.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * MainFragment

 * @author andyqtchen <br></br>
 * *         MainFragment
 * *         创建日期：2017/6/7 15:29
 */
class GankRandomFragment : BaseFragment() {

    private val mAdapter = LinearAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_random_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        loadRandomDataList()
    }

    private fun init() {
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

        mRecyclerView.adapter = mAdapter

        mSwipeRefreshLayout.setOnRefreshListener {
            loadRandomDataList()
        }

        mAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                val model = mAdapter.getItem(position)
                WebBrowserActivity.startActivity(this@GankRandomFragment, model.desc, model.url)
            }
        })

        // 首次进入，转个菊花先
        mSwipeRefreshLayout.isRefreshing = true
    }

    private fun loadRandomDataList() {
        addSubscription(ApiClient.retrofit().loadRandomData("Android", 20))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onResponse(event: ApiEvent<ApiResponse<GankModel>>) {
        mSwipeRefreshLayout.isRefreshing = false

        if (event.isSuccess) {
            if (event.body?.isError!!) {
                ToastUtils.toast(context, R.string.server_error)
                return
            }
            mAdapter.setAllItemsAndRefresh(event.body?.results!!)
        } else {
            ToastUtils.toast(context, event.errorMsg)
        }
    }

    override fun isRegisterDispatcher(): Boolean {
        return true
    }

    private inner class LinearAdapter : ListBaseAdapter<GankModel, ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, data: GankModel, position: Int) {
            holder.bindView(data, position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, context: Context, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_gank, parent, false))
        }
    }

    private inner class ViewHolder(itemView: View) : BaseViewHolder<GankModel>(itemView) {
        override fun bindView(data: GankModel, position: Int) = with(itemView) {
            if (data.images == null || data.images.isEmpty()) {
                ivPic.visibility = View.GONE
            } else {
                ivPic.visibility = View.VISIBLE
                Glide.with(itemView.context)
                        .load(data.images[0])
                        .centerCrop()
                        .into(ivPic)
            }
            tvTitle.text = data.desc
            tvTime.text = data.createdAt
            tvType.text = data.type
        }
    }
}
