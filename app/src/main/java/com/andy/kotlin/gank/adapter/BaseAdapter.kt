package com.andy.kotlin.gank.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * LazyAdapter

 * @author andyqtchen <br></br>
 * *         创建日期：2017/6/5 16:28
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = onCreateViewHolder(parent, parent.context, viewType)
        viewHolder.itemView.setOnClickListener { v ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(viewHolder.adapterPosition, v)
            }
        }

        viewHolder.itemView.setOnLongClickListener { v ->
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener!!.onItemLongClick(viewHolder.adapterPosition, v)
            }
            false
        }
        return viewHolder
    }

    abstract fun onCreateViewHolder(parent: ViewGroup, context: Context, viewType: Int): VH

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int, view: View)
    }
}