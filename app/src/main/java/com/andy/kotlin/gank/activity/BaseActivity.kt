package com.andy.kotlin.gank.activity

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

abstract class BaseActivity : AppCompatActivity() {

    val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            // 取消注册
            mCompositeSubscription.unsubscribe()
        }
        super.onDestroy()
    }

    open fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
