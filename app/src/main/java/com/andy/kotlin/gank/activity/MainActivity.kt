package com.andy.kotlin.gank.activity

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.fragment.GankHistoryFragment
import com.andy.kotlin.gank.fragment.GankHomeFragment
import com.andy.kotlin.gank.fragment.GankMeiziFragment
import com.andy.kotlin.gank.util.ActivityUtil
import com.andy.kotlin.gank.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val MIN_TO_EXIT_MILLIS = 1000L
    private var mLastOnBackPressedTime = 0L
    private var mPagerAdapter: FragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        mPagerAdapter = MainFragmentPagerAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = mPagerAdapter?.count!!
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onBackPressed() {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - mLastOnBackPressedTime < MIN_TO_EXIT_MILLIS) {
            super.onBackPressed()
        } else {
            mLastOnBackPressedTime = currentTimeMillis
            ToastUtils.toast(applicationContext, R.string.press_the_exit_application_again)
        }
    }

    private class MainFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        val pageTitleList = arrayOf("精品", "福利", "历史")
        val fragmentList = arrayOfNulls<Fragment>(pageTitleList.size)

        override fun getItem(position: Int): Fragment {
            var fragment = fragmentList[position]
            if (fragment == null) {
                fragment = when (position) {
                    0 -> GankHomeFragment()
                    1 -> GankMeiziFragment()
                    2 -> GankHistoryFragment()
                    else -> null
                }
                fragmentList[position] = fragment
            }
            return fragment!!
        }

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence = pageTitleList[position]
    }

    companion object {
        fun startActivity(aty: Activity) {
            ActivityUtil.startActivity(aty, MainActivity::class.java)
        }
    }
}