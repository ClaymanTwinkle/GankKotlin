package com.andy.kotlin.gank.fragment

import android.support.v4.app.Fragment
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

    override fun onDestroyView() {
        super.onDestroyView()
        if (mCompositeSubscription.hasSubscriptions()) {
            // 取消注册
            mCompositeSubscription.unsubscribe()
        }
    }

    open fun <M> addSubscription(observable: Observable<M>, subscriber: Subscriber<M>) {
        mCompositeSubscription.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber)
        )
    }
}
