package com.andy.kotlin.gank.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.andy.kotlin.gank.net.ApiEventCallBack
import com.andy.kotlin.gank.util.Dispatcher
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

abstract class BaseActivity : AppCompatActivity() {

    val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isRegisterDispatcher()) {
            Dispatcher.register(this)
        }
    }

    override fun onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            // 取消注册
            mCompositeSubscription.unsubscribe()
        }
        Dispatcher.unRegister(this)
        super.onDestroy()
    }

    open fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber)
        )
    }

    open fun <M> addSubscription(observable: Observable<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ApiEventCallBack<M>())
        )
    }

    abstract fun isRegisterDispatcher(): Boolean
}
