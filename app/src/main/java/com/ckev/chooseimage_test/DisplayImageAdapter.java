package com.ckev.chooseimage_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.ckev.chooseimagelibrary.base.view.ClickableAdapter;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.List;

/**
 * Created by ckerv on 16/10/14.
 */
public class DisplayImageAdapter extends ClickableAdapter<DisplayImageAdapter.DisplayImageViewHolader> {


    private List<String> mDatas;

    private Context mContext;

    private int mItemLayoutId;

    private int mSelectedNum;

    public DisplayImageAdapter(Context context, int itemLayoutId, List<String> datas, int selectedNum) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.mDatas = datas;
        this.mSelectedNum = selectedNum;
    }

    @Override
    public DisplayImageViewHolader onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DisplayImageViewHolader(LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false));
    }

    @Override
    public void onBindVH(DisplayImageViewHolader holder, int position) {
        holder.itemView.setTag(mDatas.get(position));
        //显示最后一个添加图片按钮
        if(mDatas.get(position).equals("android.resource://com.ckev.chooseimage_test/drawable/chose_add")) {
            holder.ivCover.setImageResource(R.drawable.chose_add);
        }
        CommonImageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(mDatas.get(position)), holder.ivCover, CommonImageLoader.NO_CACHE_OPTIONS);
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class DisplayImageViewHolader extends RecyclerView.ViewHolder {

        ImageView ivCover;

        public DisplayImageViewHolader(View itemView) {
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.display_img_iv);
        }
    }
}
