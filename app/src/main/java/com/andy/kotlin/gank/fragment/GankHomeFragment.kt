package com.andy.kotlin.gank.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import com.andy.kotlin.gank.Constant
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.activity.LookPictureActivity
import com.andy.kotlin.gank.activity.WebBrowserActivity
import com.andy.kotlin.gank.adapter.BaseExpandableListAdapter
import com.andy.kotlin.gank.db.DBManager
import com.andy.kotlin.gank.image.ImageLoader
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.*
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
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
open class GankHomeFragment : BaseFragment() {

    private var mAdapter: GankDayAdapter? = null
    private var mHeaderViewPresenter: HeaderViewPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day_gank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListeners()
        mSwipeRefreshLayout.isRefreshing = true
        loadDateData()
    }

    protected open fun initData() {
        mHeaderViewPresenter = HeaderViewPresenter()

        mAdapter = GankDayAdapter(context)
        mExListView.setAdapter(mAdapter)
    }

    private fun initListeners() {
        mExListView.setOnGroupClickListener { parent, v, groupPosition, id -> true }
        mExListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            val data = mAdapter!!.getChild(groupPosition, childPosition)!!
            WebBrowserActivity.startActivity(this@GankHomeFragment, data.desc!!, data.url!!)
            DBManager.sLazyDB?.insertOrUpdate(data)
            true
        }

        mExListView.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var mIsPauseRequests = false

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                when (scrollState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                        mIsPauseRequests = true
                        ImageLoader.pauseRequests(context)
                    }
                    else -> {
                        if (mIsPauseRequests) {
                            mIsPauseRequests = false
                            ImageLoader.resumeRequests(context)
                        }
                    }
                }
            }

        })

        mSwipeRefreshLayout.setOnRefreshListener {
            loadDateData()
        }

    }

    protected open fun loadDateData() {
        addSubscription(ApiClient.retrofit().loadAllHistoryDayList().flatMap { apiResponse ->
            var today = Date()
            if (!apiResponse!!.isError) {
                val theNewTimeStr = apiResponse.results?.first()
                today = DateUtil.parse(theNewTimeStr, "yyyy-MM-dd")
            }
            val year = DateUtil.getYear(today)
            val month = DateUtil.getMonth(today) + 1
            val day = DateUtil.getDay(today)
            ApiClient.retrofit().loadDateData(year, month, day)

        }, LoadDataCallBack())
    }

    protected inner class LoadDataCallBack : ApiCallBack<ApiResponse<HashMap<String, List<GankModel>>>>() {
        override fun onSuccess(modelList: ApiResponse<HashMap<String, List<GankModel>>>) {
            if (modelList.isError) {
                ToastUtils.toast(context, R.string.server_error)
            } else {
                GLog.d(modelList.results.toString())
                mAdapter!!.setData(modelList.results!!)
                mHeaderViewPresenter?.setData(modelList.results!!)
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
            mHeaderView?.visibility = GONE
            mRollPagerAdapter = RollPagerAdapter(mHeaderView!!)
            mHeaderView?.setOnItemClickListener { position: Int ->
                val data = mRollPagerAdapter!!.getData(position)
                LookPictureActivity.startActivity(this@GankHomeFragment, data.url!!)
            }
            mExListView.addHeaderView(mHeaderView)
        }

        fun setData(data: HashMap<String, List<GankModel>>) {
            val gank = data[mPicKey]
            if (gank != null && gank.isNotEmpty()) {
                mRollPagerAdapter?.setData(gank)
                mHeaderView?.setAdapter(mRollPagerAdapter)
                mHeaderView?.visibility = VISIBLE
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

        val picWidth:Int
        val picHeight:Int

        init {
            picWidth = ScreenUtil.getWindowScreenWidth(viewPager.context)
            picHeight = DensityUtils.dip2px(viewPager.context, 210.toFloat())
        }

        fun setData(dataList: List<GankModel>) {
            mDataList.clear()
            mDataList.addAll(dataList)
            notifyDataSetChanged()
        }

        override fun getView(container: ViewGroup?, position: Int): View {
            val view = ImageView(container?.context)
            view.scaleType = ImageView.ScaleType.CENTER_CROP
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            ImageLoader.loadImage(view, mDataList[position].url, picWidth, picHeight)

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

        fun getGroupList(): ArrayList<String> = mGroupList

        override fun getGroup(groupPosition: Int): Any = mGroupList[groupPosition]

        override fun getGroupCount(): Int = mGroupList.size

        override fun getChildrenCount(groupPosition: Int): Int = mDataList[mGroupList[groupPosition]]!!.size

        override fun getChild(groupPosition: Int, childPosition: Int): GankModel? = mDataList[mGroupList[groupPosition]]?.get(childPosition)

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
            if (child.images == null || child.images!!.isEmpty()) {
                ivPic.visibility = View.GONE
            } else {
                ImageLoader.loadImage(ivPic,child.images!![0])
                ivPic.visibility = View.VISIBLE
                ivPic.setOnClickListener {
                    LookPictureActivity.startActivity(context as Activity, child.images!![0])
                }
            }
            tvTitle.text = child.desc
            tvTime.text = DateUtil.format(child.createdAt, Constant.GANK_TIME_FORMAT, DateUtil.YYYY_MM_DD_HH_MM_SS)
            tvType.text = child.type
        }
    }
}
