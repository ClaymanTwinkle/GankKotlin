package com.andy.kotlin.gank.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.db.SPManager
import com.andy.kotlin.gank.model.GankModel
import com.andy.kotlin.gank.net.ApiResponse
import com.andy.kotlin.gank.util.GLog
import com.andy.kotlin.gank.util.ToastUtils
import com.andy.kotlinandroid.net.ApiCallBack
import com.andy.kotlinandroid.net.ApiClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.Exception

class SplashActivity : BaseActivity() {

    private var mPicUrl = ""
    private var mSPManager: SPManager? = null
    private var mIsPreLoaded = false
    private val delayMillis:Long = 3000

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

    private fun loadImage(picUrl:String){
        Glide.with(this)
                .load(picUrl)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        mIvPic.setImageBitmap(resource)
                        doAfterLoaded()
                    }

                    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                        super.onLoadFailed(e, errorDrawable)
                        GLog.e(e)
                        doAfterLoaded()
                    }
                })
    }

    private fun doAfterLoaded() {
        if(mIsPreLoaded){
            mIsPreLoaded = false
            loadData()
        } else {
            mSPManager?.setSplashPic(mPicUrl)
            Handler(Looper.getMainLooper()).postDelayed({ startMainActivity() }, delayMillis)
        }
    }

    private fun startMainActivity() {
        MainActivity.startActivity(this@SplashActivity)
        finish()
    }
}
