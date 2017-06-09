package com.andy.kotlin.gank.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.kotlin.gank.adapter.base.BaseViewHolder;
import com.andy.kotlin.gank.adapter.base.ListBaseAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * CommonAdapter
 *
 * @author andyqtchen <br/>
 *         通用的Adapter
 *         创建日期：2017/6/9 11:16
 */
public abstract class CommonAdapter<T> extends ListBaseAdapter<T, CommonAdapter.ViewHolder> {

    private final
    @LayoutRes
    int mItemLayoutId;

    public CommonAdapter(int mItemLayoutId) {
        this.mItemLayoutId = mItemLayoutId;
    }

    @Override
    public void onBindViewHolder(@NotNull CommonAdapter.ViewHolder holder, T data, int position) {
        holder.bindView(data, position);
    }

    @NotNull
    @Override
    public CommonAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, @NotNull Context context, int viewType) {
        return new CommonAdapter.ViewHolder(LayoutInflater.from(context).inflate(mItemLayoutId, parent, false));
    }

    class ViewHolder extends BaseViewHolder<T> {

        ViewHolder(@NotNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(T data, int position) {
            CommonAdapter.this.bindView(itemView, data, position);
        }
    }

    protected abstract void bindView(View itemView, T data, int position);
}
