package com.andy.kotlin.gank.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.activity.WebBrowserActivity
import com.andy.kotlin.gank.adapter.BaseExpandableListAdapter
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.DateUtil
import com.andy.kotlin.gank.util.GLog
import com.andy.kotlin.gank.util.LoggerRequestListener
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter
import kotlinx.android.synthetic.main.fragment_day_gank.*
import kotlinx.android.synthetic.main.list_item_content_gank_list.view.*
import kotlinx.android.synthetic.main.list_item_title_gank_list.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * GankDayFragment

 * @author andyqtchen <br></br>
 * *         天天精品
 * *         创建日期：2017/6/9 10:22
 */
class GankDayFragment : BaseFragment() {

    private var mAdapter: GankDayAdapter? = null
    private var mHeaderViewPresenter: HeaderViewPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        loadDateData()
    }

    private fun init() {
        mHeaderViewPresenter = HeaderViewPresenter()

        mAdapter = GankDayAdapter(context)
        mExListView.setAdapter(mAdapter)
        mExListView.setOnGroupClickListener { parent, v, groupPosition, id -> true }
        mExListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val data = mAdapter!!.getChild(groupPosition, childPosition)!!
            WebBrowserActivity.startActivity(this@GankDayFragment, data.desc, data.url)
            true
        }

        mSwipeRefreshLayout.setOnRefreshListener {
            loadDateData()
        }

        mSwipeRefreshLayout.isRefreshing = true
    }

    private fun loadDateData() {

        addSubscription(ApiClient.retrofit().loadAllHistoryDayList().flatMap { apiResponse ->
            var today = Date()
            if (!apiResponse?.isError!!) {
                val theNewTimeStr = apiResponse.results?.first()
                today = DateUtil.parse(theNewTimeStr, "yyyy-MM-dd")
            }
            val year = DateUtil.getYear(today)
            val month = DateUtil.getMonth(today)
            val day = DateUtil.getDay(today)
            ApiClient.retrofit().loadDateData(year, month, day)

        }, object : ApiCallBack<ApiResponse<HashMap<String, List<GankModel>>>>() {
            override fun onSuccess(model: ApiResponse<HashMap<String, List<GankModel>>>) {
                if (model.isError) {
                    ToastUtils.toast(context, R.string.server_error)
                } else {
                    GLog.d(model.results.toString())
                    mAdapter!!.setData(model.results!!)
                    mHeaderViewPresenter?.setData(model.results!!)
                    for (i in mAdapter!!.getGroupList().indices) {
                        mExListView.expandGroup(i)
                    }
                }
            }

            override fun onFailure(code: Int, msg: String?) {
                mHeaderViewPresenter?.hideHeader()
                ToastUtils.toast(context, msg)
            }

            override fun onFinish() {
                mSwipeRefreshLayout.isRefreshing = false
            }
        })
    }

    /**
     * 用于处理Header View的Presenter
     */
    inner class HeaderViewPresenter {
        private var mPicKey: String = "福利"
        private var mHeaderView: RollPagerView? = null
        private var mRollPagerAdapter: RollPagerAdapter? = null

        init {
            mHeaderView = LayoutInflater.from(context).inflate(R.layout.list_item_header_pic_bg, mExListView, false) as RollPagerView
            mRollPagerAdapter = RollPagerAdapter(mHeaderView!!)
            mHeaderView?.setOnItemClickListener { position: Int ->
                val data = mRollPagerAdapter!!.getData(position)
                WebBrowserActivity.startActivity(this@GankDayFragment, data.desc, data.url)
            }
        }

        fun setData(data: HashMap<String, List<GankModel>>) {
            val gank = data[mPicKey]
            if (gank != null && gank.isNotEmpty()) {
                mRollPagerAdapter?.setData(gank)
                mHeaderView?.setAdapter(mRollPagerAdapter)
                mHeaderView?.visibility = VISIBLE
                if (mExListView.headerViewsCount == 0) {
                    mExListView.addHeaderView(mHeaderView)
                }
            }
        }

        fun hideHeader() {
            mHeaderView?.visibility = GONE
        }
    }

    /**
     * 轮播view pager的适配器
     */
    class RollPagerAdapter(viewPager: RollPagerView) : LoopPagerAdapter(viewPager) {
        private val mDataList = ArrayList<GankModel>()

        fun setData(dataList: List<GankModel>) {
            mDataList.clear()
            mDataList.addAll(dataList)
        }

        override fun getView(container: ViewGroup?, position: Int): View {
            val view = ImageView(container?.context)
            view.scaleType = ImageView.ScaleType.CENTER_CROP
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            Glide.with(container?.context)
                    .load(mDataList[position].url)
                    .centerCrop()
                    .into(view)

            return view
        }

        override fun getRealCount(): Int = mDataList.size

        fun getData(position: Int): GankModel = mDataList[position]
    }

    /**
     * 列表的适配器
     */
    class GankDayAdapter(context: Context) : BaseExpandableListAdapter() {
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

        fun getGroupList(): ArrayList<String> {
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

        fun bindGroupView(group: String, groupView: View) = with(groupView) {
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

        fun bindChildView(child: GankModel, childView: View) = with(childView) {
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
