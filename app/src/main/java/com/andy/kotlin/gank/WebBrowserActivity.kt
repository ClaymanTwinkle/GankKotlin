package com.andy.kotlin.gank

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import com.andy.kotlinandroid.BaseActivity
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


        mWebView.settings.javaScriptEnabled = true
        mWebView.setWebViewClient(WebViewClient())
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
    }
}
