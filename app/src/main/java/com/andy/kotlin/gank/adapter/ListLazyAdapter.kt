package com.andy.kotlin.gank.adapter

import android.support.v7.widget.RecyclerView
import java.util.*

/**
 * ListLazyAdapter
 * @author andyqtchen <br></br>
 * *         创建日期：2017/6/5 16:31
 */
abstract class ListLazyAdapter<T, VH : RecyclerView.ViewHolder> : LazyAdapter<VH>() {

    private val mData = ArrayList<T>()

    abstract fun onBindViewHolder(holder: VH, data: T, position: Int)

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, mData[position], position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun getItem(index: Int): T {
        return mData[index]
    }

    fun removeItem(index: Int) {
        this.mData.removeAt(index)
    }

    fun removeItemAndRefresh(index: Int) {
        this.mData.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeItem(data: T) {
        this.mData.remove(data)
    }

    fun removeItemAndRefresh(data: T) {
        val index = this.mData.indexOf(data)
        if (index > -1) {
            this.mData.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun removeAllItems(data: List<T>) {
        this.mData.removeAll(data)
    }

    fun removeAllItemsAndRefresh(data: List<T>) {
        for (i in mData.indices) {
            if (data.contains(mData[i])) {
                this.mData.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    fun addAllItems(data: List<T>) {
        this.mData.addAll(data)
    }

    fun addAllItemsAndRefresh(data: List<T>) {
        val oldSize = mData.size
        this.mData.addAll(data)
        notifyItemRangeInserted(oldSize, data.size)
    }

    fun addItem(data: T) {
        this.mData.add(data)
    }

    fun addItemAndRefresh(data: T) {
        val oldSize = mData.size
        this.mData.add(data)
        notifyItemInserted(oldSize)
    }

    fun addItem(index: Int, data: T) {
        this.mData.add(index, data)
    }

    fun addItemAndRefresh(index: Int, data: T) {
        this.mData.add(index, data)
        notifyItemInserted(index)
    }

    fun setItem(index: Int, data: T) {
        this.mData[index] = data
    }

    fun setItemAndRefresh(index: Int, data: T) {
        this.mData[index] = data
        notifyItemChanged(index)
    }

    fun setAllItems(data: List<T>) {
        this.mData.clear()
        this.mData.addAll(data)
    }

    fun setAllItemsAndRefresh(data: List<T>) {
        this.mData.clear()
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    fun clearAllItems() {
        this.mData.clear()
    }

    fun clearAllItemsAndRefresh() {
        this.mData.clear()
        notifyDataSetChanged()
    }
}