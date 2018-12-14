package com.andy.kotlin.gank.activity

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.db.SPManager
import com.andy.kotlin.gank.image.ImageLoader
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.LoggerRequestListener
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private var mPicUrl = ""
    private var mSPManager: SPManager? = null
    private var mIsPreLoaded = false
    private val mDelayMillis: Long = 2000
    private val mHandler: Handler = Handler()
    private val startMainActivityTask = Runnable { startMainActivity() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initData()
    }

    private fun initData() {
        mSPManager = SPManager(applicationContext)
        mPicUrl = mSPManager?.getSplashPic()!!

        mIsPreLoaded = !TextUtils.isEmpty(mPicUrl)
        if (mIsPreLoaded) {
            loadImage(mPicUrl)
        } else {
            loadData()
        }
    }

    private fun loadData() {
        addSubscription(ApiClient.retrofit().loadRandomData("福利", 1), object : ApiCallBack<ApiResponse<List<GankModel>>>() {
            override fun onFinish() {
                if (!TextUtils.isEmpty(mPicUrl)) {
                    loadImage(mPicUrl)
                }
            }

            override fun onFailure(code: Int, msg: String?) {
                startMainActivity()
            }

            override fun onSuccess(modelList: ApiResponse<List<GankModel>>) {
                if (modelList.isError) {
                    ToastUtils.toast(applicationContext, R.string.server_error)
                } else {
                    if (modelList.results == null || modelList.results!!.isEmpty()) {
                        return
                    }
                    mPicUrl = modelList.results!![0].url!!
                }
            }
        })
    }

    private fun loadImage(picUrl: String) {
        ImageLoader.loadImage(this, mIvPic, picUrl, object : LoggerRequestListener() {
            override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                val result = super.onResourceReady(resource, model, target, isFromMemoryCache, isFirstResource)
                doAfterLoaded()
                return result
            }

            override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                val result = super.onException(e, model, target, isFirstResource)
                doAfterLoaded()
                return result
            }
        })
    }

    private fun doAfterLoaded() {
        if (mIsPreLoaded) {
            mIsPreLoaded = false
            loadData()
        } else {
            mSPManager?.setSplashPic(mPicUrl)
            mHandler.postDelayed(startMainActivityTask, mDelayMillis)
        }
    }

    private fun startMainActivity() {
        MainActivity.startActivity(this@SplashActivity)
        finish()
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(startMainActivityTask)
        super.onDestroy()
    }
}
