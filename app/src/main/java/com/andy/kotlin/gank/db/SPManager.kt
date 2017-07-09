package com.andy.kotlin.gank.db

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences
 * Created by Andy on 2017/7/9.
 */
class SPManager(context: Context) {

    private val SP_NAME = "default"
    private val KEY_SPLASH_PIC = "key_splash_pic"
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun getSplashPic() = mSharedPreferences.getString(KEY_SPLASH_PIC, "")

    fun setSplashPic(value: String): Boolean {
       return mSharedPreferences
                .edit()
                .putString(KEY_SPLASH_PIC, value)
                .commit()
    }
}
