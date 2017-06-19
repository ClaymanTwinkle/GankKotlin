package com.andy.kotlin.gank

import android.app.Application
import android.content.Context

/**
 * GankApplication
 * @author andyqtchen <br></br>
 * *         创建日期：2017/6/19 15:13
 */
class GankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: Context? = null
            private set
    }
}
