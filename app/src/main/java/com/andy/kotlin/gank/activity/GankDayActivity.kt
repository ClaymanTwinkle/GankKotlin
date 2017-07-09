package com.andy.kotlin.gank.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.andy.kotlin.gank.R
import com.andy.kotlin.gank.fragment.GankDayFragment
import java.util.*

class GankDayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gank_day)

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, GankDayFragment.newInstance(intent.getSerializableExtra(EXTRA_TIME) as Date))
                .commit()
    }

    companion object {
        private val EXTRA_TIME = "extra_time"

        fun startActivity(aty: Activity, time: Date) {
            aty.startActivity(Intent(aty, GankDayActivity::class.java)
                    .putExtra(EXTRA_TIME, time))
        }
    }
}