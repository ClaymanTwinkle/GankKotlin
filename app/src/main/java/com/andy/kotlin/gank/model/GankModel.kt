package com.andy.kotlin.gank.model

/**
 * todo 类名

 * @author andyqtchen <br></br>
 * *         gank 主要的数据结构
 * *         创建日期：2017/6/5 13:36
 */
data class GankModel(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val images: List<String>?,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)
