package com.andy.kotlin.gank.model

import org.kesar.lazy.lazydb.annotate.ID
import java.io.Serializable

/**
 * GankModel
 * @author andyqtchen <br></br>
 * *         gank 主要的数据结构
 * *         创建日期：2017/6/5 13:36
 */
class GankModel:Serializable{
        @ID
        var _id: String? = null
        var createdAt: String? = null
        var desc: String? = null
        var images: List<String>? = null
        var publishedAt: String? = null
        var source: String? = null
        var type: String? = null
        var url: String? = null
        var used: Boolean? = null
        var who: String? = null
}
