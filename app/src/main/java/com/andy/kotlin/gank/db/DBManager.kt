package com.andy.kotlin.gank.db

import android.content.Context
import org.kesar.lazy.lazydb.LazyDB
import org.kesar.lazy.lazydb.config.DBConfig

/**
 * DataBase Manager
 * Created by Andy on 2017/7/8.
 */
object DBManager {
    var sLazyDB: LazyDB? = null

    fun init(context: Context) {
        DBConfig.Builder(context)
                .setDebug(true)

        sLazyDB = LazyDB.create(
                DBConfig.Builder(context)
                .setDebug(true)
                        .setDataBaseName("app.db")
                        .setDatabaseVersion(1)
                        .build()
        )
    }
}
