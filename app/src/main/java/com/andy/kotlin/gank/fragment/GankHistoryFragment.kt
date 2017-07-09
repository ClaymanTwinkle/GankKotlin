package com.andy.kotlin.gank.fragment

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.activity.LookPictureActivity
import com.andy.kotlin.gank.activity.WebBrowserActivity
import com.andy.kotlin.gank.adapter.CommonAdapter
import com.andy.kotlin.gank.adapter.base.BaseAdapter
import com.andy.kotlin.gank.db.DBManager
import com.andy.kotlin.gank.image.GlideOnScrollListener
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.util.DensityUtils
import com.andy.kotlin.gank.util.LoggerRequestListener
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiCallBack
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_random_gank.*
import kotlinx.android.synthetic.main.list_item_gank.view.*
import rx.Observable



/**
 * GankHistoryFragment

 * @author andyqtchen <br></br>
 * *         GankHistoryFragment
 * *         创建日期：2017/6/7 15:29
 */
class GankHistoryFragment : BaseFragment() {

    private var mAdapter: LinearAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_random_gank, container, false)
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

        mAdapter = LinearAdapter()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnScrollListener(GlideOnScrollListener(context))

        mSwipeRefreshLayout.setOnRefreshListener {
            loadRandomDataList()
        }

        mAdapter!!.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                val model = mAdapter!!.getItem(position)
                WebBrowserActivity.startActivity(this@GankHistoryFragment, model.desc!!, model.url!!)
            }
        })

        // 首次进入，转个菊花先
        mSwipeRefreshLayout.isRefreshing = true
    }

    private fun loadRandomDataList() {
        addSubscription(Observable.create { subscriber ->
            subscriber?.onNext(DBManager.mLazyDB?.query(GankModel::class.java)?.findAll())
            subscriber?.onCompleted()
        }, object : ApiCallBack<List<GankModel>>(){
            override fun onFailure(code: Int, msg: String?) {
                ToastUtils.toast(context, msg)
            }

            override fun onFinish() {
                mSwipeRefreshLayout.isRefreshing = false
            }

            override fun onSuccess(modelList: List<GankModel>) {
                    mAdapter!!.setAllItemsAndRefresh(modelList)
            }
        })
    }

    private inner class LinearAdapter : CommonAdapter<GankModel>(R.layout.list_item_gank) {
        override fun bindView(itemView: View, data: GankModel?, position: Int) = with(itemView) {
            if (data!!.images == null || data.images!!.isEmpty()) {
                ivPic.visibility = View.GONE
            } else {
                Glide.with(context)
                        .load(data.images!![0])
                        .listener(LoggerRequestListener())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(ivPic)
                ivPic.visibility = View.VISIBLE
                ivPic.setOnClickListener{
                    LookPictureActivity.startActivity(context as Activity, data.images!![0])
                }
            }
            tvTitle.text = data.desc
            tvTime.text = data.createdAt
            tvType.text = data.type
        }
    }
}
