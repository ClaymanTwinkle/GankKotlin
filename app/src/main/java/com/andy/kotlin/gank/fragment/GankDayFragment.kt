package com.andy.kotlin.gank.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.activity.WebBrowserActivity
import com.andy.kotlin.gank.adapter.BaseExpandableListAdapter
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.GLog
import com.andy.kotlin.gank.util.LoggerRequestListener
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_day_gank.*
import kotlinx.android.synthetic.main.list_item_content_gank_list.view.*
import kotlinx.android.synthetic.main.list_item_title_gank_list.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * GankDayFragment

 * @author andyqtchen <br></br>
 * *         天天精品
 * *         创建日期：2017/6/9 10:22
 */
class GankDayFragment : BaseFragment() {

    private var mAdapter: GankDayAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_day_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        mAdapter = GankDayAdapter()
        mExListView.setAdapter(mAdapter)
        mExListView.setOnGroupClickListener { parent, v, groupPosition, id -> true }
        mExListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val data = mAdapter!!.getChild(groupPosition, childPosition)!!
            WebBrowserActivity.startActivity(this@GankDayFragment, data.desc, data.url)
            true
        }
        loadDateData()
    }

    private fun loadDateData() {
        addSubscription(ApiClient.retrofit().loadDateData(2015, 8, 7), object : ApiCallBack<ApiResponse<HashMap<String, List<GankModel>>>>() {
            override fun onSuccess(model: ApiResponse<HashMap<String, List<GankModel>>>) {
                if (model.isError) {
                    ToastUtils.toast(context, R.string.server_error)
                } else {
                    GLog.d(model.results.toString())
                    mAdapter!!.setData(model.results!!)
                    for (i in mAdapter!!.getGroupList().indices){
                        mExListView.expandGroup(i)
                    }
                }
            }

            override fun onFailure(code: Int, msg: String?) {
                ToastUtils.toast(context, msg)
            }

            override fun onFinish() {
            }
        })
    }

    inner class GankDayAdapter : BaseExpandableListAdapter() {
        private var mDataList = HashMap<String, List<GankModel>>()
        private var mGroupList = ArrayList<String>()
        private var mInflater: LayoutInflater? = null

        init {
            mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        fun setData(data: HashMap<String, List<GankModel>>) {
            this.mDataList = data
            mGroupList.clear()
            mGroupList.addAll(this.mDataList.keys)
        }

        fun getGroupList():ArrayList<String> {
            return mGroupList
        }

        override fun getGroup(groupPosition: Int): Any {
            return mGroupList[groupPosition]
        }

        override fun getGroupCount(): Int {
            return mGroupList.size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return mDataList[mGroupList[groupPosition]]?.size!!
        }

        override fun getChild(groupPosition: Int, childPosition: Int): GankModel? {
            return mDataList[mGroupList[groupPosition]]?.get(childPosition)
        }

        override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
            var groupView = convertView
            val group = mGroupList[groupPosition]

            if (groupView == null) {
                groupView = mInflater?.inflate(R.layout.list_item_title_gank_list, parent, false)
            }
            bindGroupView(group, groupView!!)
            return groupView
        }

        fun bindGroupView(group:String,groupView:View) = with(groupView){
            tvGroupTitle.text = group
        }

        override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
            var childView = convertView
            val group = mGroupList[groupPosition]
            val child = mDataList[group]?.get(childPosition)

            if (childView == null) {
                childView = mInflater?.inflate(R.layout.list_item_content_gank_list, parent, false)
            }

            bindChildView(child!!, childView!!)

            return childView
        }

        fun bindChildView(child:GankModel, childView:View) = with(childView){
            if (child.images == null || child.images.isEmpty()) {
                ivPic.visibility = View.GONE
            } else {
                ivPic.visibility = View.VISIBLE
                Glide.with(context)
                        .load(child.images[0])
                        .listener(LoggerRequestListener())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(ivPic)
            }
            tvTitle.text = child.desc
            tvTime.text = child.createdAt
            tvType.text = child.type
        }

    }
}
