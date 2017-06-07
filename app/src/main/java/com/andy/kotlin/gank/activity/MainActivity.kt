package com.andy.kotlin.gank

import android.os.Bundle
import com.andy.kotlin.gank.fragment.MainFragment
import com.andy.kotlinandroid.BaseActivity


class MainActivity : BaseActivity() {
    override fun isRegisterDispatcher(): Boolean {
        return false
    }

    var mMainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMainFragment = MainFragment()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.llRootLayout, mMainFragment, MainFragment::class.java.toString())
                .commit()
    }
}