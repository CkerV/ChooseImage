package com.ckev.chooseimagelibrary.base.img.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckev.chooseimagelibrary.R;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageFolderChangeListener;
import com.ckev.chooseimagelibrary.base.view.BasePopupWindowForRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.List;

/**
 * 展示图片文件夹的弹窗
 * Created by ckerv on 16/10/11.
 */
public class ImageFolderPopupWindow extends BasePopupWindowForRecyclerView<ImageFolderBean, ImageFolderPopupWindow.ImageFolderViewHolder> {

    private final static String TAG = "ImageFolderPopupWindow";

    /**
     * 外部接口,通过set进内部,以实现图片文件夹item点击后的相关方法回调
     */
    private OnImageFolderChangeListener mOnImageFolderChangeListener;

    public ImageFolderPopupWindow(View contentView, int width, int height, int recyclerViewId, boolean focusable, List<ImageFolderBean> datas) {
        super(contentView, width, height, recyclerViewId, focusable, datas);
        this.mOnImageFolderChangeListener = new DefaultOnImgFolderChangeListener();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    public void setOnImageFolderChangeListener(OnImageFolderChangeListener onImageFolderChangeListener) {
        this.mOnImageFolderChangeListener = onImageFolderChangeListener;
    }

    @Override
    protected void onBindVH(ImageFolderViewHolder holder, int position) {
        holder.ivCover.setImageResource(R.drawable.base_img_null);
        ImageFolderBean item = mDatas.get(position);
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(item.getFirstImagePath())
        ,holder.ivCover, CommonImageLoader.MEMORY_CACHE_OPTIONS);
        holder.tvCount.setText(item.getCount() + "张");
        holder.tvName.setText(item.getName());
    }

    @Override
    protected ImageFolderViewHolder onCreateViewHolader(ViewGroup parent, int viewType) {
        return new ImageFolderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_choose_img_popup_folder, parent,false));
    }

    @Override
    public void init() {

    }

    public class ImageFolderViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCover;
        TextView tvCount;
        TextView tvName;

        public ImageFolderViewHolder(View itemView) {
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.choose_img_popup_iv_folder);
            tvCount = (TextView) itemView.findViewById(R.id.choose_img_popup_tv_folder_count);
            tvName = (TextView) itemView.findViewById(R.id.choose_img_popup_tv_folder_name);
        }
    }

    @Override
    protected void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        mOnImageFolderChangeListener.onImageFolderChange(mDatas.get(position));
    }

    /**
     * OnImageFolderChangeListener的默认实现类
     */
    class DefaultOnImgFolderChangeListener implements OnImageFolderChangeListener {

        @Override
        public void onImageFolderChange(ImageFolderBean currentFolder) {
            Log.d(TAG, "onImageFolderChange");
        }
    }
}
