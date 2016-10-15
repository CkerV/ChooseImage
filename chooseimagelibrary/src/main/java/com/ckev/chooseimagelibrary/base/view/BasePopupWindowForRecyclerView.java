package com.ckev.chooseimagelibrary.base.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 弹窗包含RecyclerView的基类,向上暴露ClickableAdapter的抽象方法
 * @see ClickableAdapter
 * Created by ckerv on 16/10/11.
 */
public abstract class BasePopupWindowForRecyclerView<T, V extends RecyclerView.ViewHolder> extends BasePopupWindow {

    protected List<T> mDatas;

    protected Context mContext;

    public BasePopupWindowForRecyclerView(View contentView, int width, int height, int recyclerViewId, boolean focusable, List<T> datas) {
        super(contentView, width, height, focusable);
        this.mDatas = datas;
        this.mContext = contentView.getContext();
        RecyclerView recyclerView = (RecyclerView) findViewById(recyclerViewId);
        recyclerView.setLayoutManager(getLayoutManager());
        ClickableAdapter adapter = new ClickableAdapter() {
            @Override
            public void onBindVH(RecyclerView.ViewHolder holder, int position) {
                BasePopupWindowForRecyclerView.this.onBindVH((V) holder, position);
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return BasePopupWindowForRecyclerView.this.onCreateViewHolader(parent, viewType);
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        };
        adapter.setOnItemClickListener(new ClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BasePopupWindowForRecyclerView.this.onItemClick(view, position);
            }
        });
        adapter.setOnLongClickListener(new ClickableAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                return BasePopupWindowForRecyclerView.this.onItemLongClick(view, position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract void onBindVH(V holder, int position);

    protected abstract V onCreateViewHolader(ViewGroup parent, int viewType);

    protected void onItemClick(View view, int position) {};

    protected boolean onItemLongClick(View view, int position) { return false;};

}
