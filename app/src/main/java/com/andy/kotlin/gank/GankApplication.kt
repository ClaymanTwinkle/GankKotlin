package com.andy.kotlin.gank

import android.app.Application
import android.content.Context
import com.andy.kotlin.gank.db.DBManager

/**
 * GankApplication
 * @author andyqtchen <br></br>
 * *         创建日期：2017/6/19 15:13
 */
class GankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        DBManager.init(this)
    }

    companion object {
        var context: Context? = null
            private set
    }
}
