package com.andy.kotlin.gank.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.andy.kotlin.gank.R
import kotlinx.android.synthetic.main.activity_web_brower.*


class WebBrowserActivity : BaseActivity() {

    var mUrl: String = ""
    var mTitle: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_brower)

        mUrl = intent.getStringExtra(EXTRA_URL)
        mTitle = intent.getStringExtra(EXTRA_TITLE)

        title = mTitle

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mWebView.setWebViewClient(WebViewClient())
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    mProgressBar.progress = newProgress
                    mProgressBar.visibility = View.GONE
                    //progressBar.setProgress(newProgress)
                } else {
                    mProgressBar.visibility = View.VISIBLE
                    mProgressBar.progress = newProgress//设置加载进度
                }
            }
        })

        mWebView.settings.useWideViewPort = true
        mWebView.settings.loadWithOverviewMode = true
        mWebView.loadUrl(mUrl)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var EXTRA_URL = "url"
        var EXTRA_TITLE = "title"

        fun startActivity(aty: Activity, title: String, url: String) {
            aty.startActivity(Intent(aty.applicationContext, WebBrowserActivity::class.java)
                    .putExtra(EXTRA_URL, url)
                    .putExtra(EXTRA_TITLE, title))
        }

        fun startActivity(fragment: Fragment, title: String, url: String) {
            fragment.startActivity(Intent(fragment.context, WebBrowserActivity::class.java)
                    .putExtra(EXTRA_URL, url)
                    .putExtra(EXTRA_TITLE, title))
        }
    }
}
