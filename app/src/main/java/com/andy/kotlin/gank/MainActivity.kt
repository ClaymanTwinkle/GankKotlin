package com.andy.kotlin.gank

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.kotlin.gank.adapter.LazyAdapter
import com.andy.kotlin.gank.adapter.ListLazyAdapter
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.DensityUtils
import com.andy.kotlinandroid.BaseActivity
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_gank.view.*


class MainActivity : BaseActivity() {

    private val mAdapter = LinearAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(object : DividerItemDecoration(this, DividerItemDecoration.VERTICAL) {
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
            addSubscription(ApiClient.retrofit().loadRandomData("Android", 20), object : ApiCallBack<ApiResponse<GankModel>>() {
                override fun onSuccess(model: ApiResponse<GankModel>) {
                    Log.d("andy", model.toString())
                    if (model.isError) {
                        return
                    }
                    mAdapter.setAllItemsAndRefresh(model.results!!)
                }

                override fun onFailure(msg: String?) {
                    Log.d("andy", "onFailure=" + msg)
                }

                override fun onFinish() {
                    Log.d("andy", "onFinish")
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }
            )
        }

        mAdapter.setOnItemClickListener(object : LazyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                val model = mAdapter.getItem(position)
                WebBrowserActivity.startActivity(this@MainActivity, model.desc, model.url)
            }
        })
    }

    private inner class LinearAdapter : ListLazyAdapter<GankModel, ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, data: GankModel, position: Int) {
            holder.bind(data, position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, context: Context, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_gank, parent, false))
        }
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: GankModel, position: Int) = with(itemView) {
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