package com.andy.kotlin.gank.activity

import android.os.Bundle
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.fragment.GankRandomFragment


class MainActivity : BaseActivity() {

    var mGankRandomFragment: GankRandomFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mGankRandomFragment = GankRandomFragment()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.llRootLayout, mGankRandomFragment, GankRandomFragment::class.java.toString())
                .commit()
    }

    override fun isRegisterDispatcher(): Boolean {
        return false
    }
}