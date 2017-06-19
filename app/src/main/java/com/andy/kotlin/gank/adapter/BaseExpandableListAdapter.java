package com.andy.kotlin.gank.adapter;


/**
 * todo 类名
 *
 * @author andyqtchen <br/>
 *         todo 实现的主要功能。
 *         创建日期：2017/6/19 11:12
 */
public abstract class BaseExpandableListAdapter extends android.widget.BaseExpandableListAdapter {

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
