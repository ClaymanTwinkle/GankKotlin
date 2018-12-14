package com.andy.kotlin.gank.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.image.ImageLoader
import kotlinx.android.synthetic.main.activity_look_picture.*

class LookPictureActivity : BaseActivity() {
    private var mUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_picture)
        initData()
        initView()
    }

    private fun initData() {
        mUrl = intent.getStringExtra(EXTRA_URL)
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ImageLoader.loadImage(this, mIvPic, mUrl)
    }

    companion object {
        private val EXTRA_URL = "extra_url"

        fun startActivity(activity: Activity, picUrl: String) {
            activity.startActivity(Intent(activity, LookPictureActivity::class.java)
                    .putExtra(EXTRA_URL, picUrl))
        }

        fun startActivity(fragment: Fragment, picUrl: String) {
            fragment.startActivity(Intent(fragment.context, LookPictureActivity::class.java)
                    .putExtra(EXTRA_URL, picUrl))
        }
    }
}
