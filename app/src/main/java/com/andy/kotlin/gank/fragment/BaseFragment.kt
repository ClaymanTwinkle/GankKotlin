package com.andy.kotlin.gank.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.andy.kotlin.gank.net.ApiEventCallBack
import com.andy.kotlin.gank.util.Dispatcher
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * BaseFragment
 * @author andyqtchen <br></br>
 * *         BaseFragment
 * *         创建日期：2017/6/7 15:28
 */
abstract class BaseFragment : Fragment() {
    val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isRegisterDispatcher()) {
            Dispatcher.register(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mCompositeSubscription.hasSubscriptions()) {
            // 取消注册
            mCompositeSubscription.unsubscribe()
        }
        Dispatcher.unRegister(this)
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
