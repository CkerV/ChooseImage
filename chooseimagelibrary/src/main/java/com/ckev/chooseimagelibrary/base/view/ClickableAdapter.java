package com.ckev.chooseimagelibrary.base.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 处理RecyclerView的item点击事件的adapter的封装
 * Created by ckerv on 16/10/9.
 */
public abstract class ClickableAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements View.OnClickListener, View.OnLongClickListener{

    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListner;

    private static final int TAG_KEY = 0x12345678;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListner = listener;
    }


    abstract public void onBindVH(V holder, int position);

    @Override
    public void onBindViewHolder(V holder, int position) {
        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(this);
        if (onItemLongClickListner != null)
            holder.itemView.setOnLongClickListener(this);
        holder.itemView.setTag(TAG_KEY, position);
        onBindVH(holder, position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(TAG_KEY);
        onItemClickListener.onItemClick(v, position);
    }

    @Override
    public boolean onLongClick(View v) {
        int position = (int) v.getTag(TAG_KEY);
        return onItemLongClickListner.onItemLongClick(v, position);
    }

}
