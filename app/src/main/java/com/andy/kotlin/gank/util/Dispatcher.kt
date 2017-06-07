package com.andy.kotlin.gank.util

import org.greenrobot.eventbus.EventBus

/**
 * Dispatcher

 * @author andyqtchen <br></br>
 * *         事件分发器
 * *         创建日期：2017/6/7 15:52
 */
object Dispatcher {
    private var mBus = EventBus.getDefault()

    fun setBus(bus: EventBus) {
        mBus = bus
    }

    fun register(o: Any) {
        if (!mBus.isRegistered(o)) {
            mBus.register(o)
        }
    }

    fun unRegister(o: Any) {
        if (mBus.isRegistered(o)) {
            mBus.unregister(o)
        }
    }

    fun post(o: Any) {
        mBus.post(o)
    }

    fun postSticky(o: Any) {
        mBus.postSticky(o)
    }

    fun removeSticky(o: Any) {
        mBus.removeStickyEvent(o)
    }
}
