package com.andy.kotlin.gank.event

/**
 * ApiEvent<T>
 * @author andyqtchen <br></br>
 *          Api返回事件
 *          创建日期：2017/6/7 16:14
 */
open class ApiEvent<T> {
    var isSuccess: Boolean = false
    var errorCode: Int = 0
    var errorMsg: String? = null
    var body: T? = null

    constructor(body: T) {
        this.body = body
        this.isSuccess = true
    }

    constructor(errorCode: Int, errorMsg: String) {
        this.errorCode = errorCode
        this.errorMsg = errorMsg
        this.isSuccess = false
    }
}
