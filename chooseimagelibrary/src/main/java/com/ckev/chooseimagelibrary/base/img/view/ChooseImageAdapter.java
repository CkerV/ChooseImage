package com.ckev.chooseimagelibrary.base.img.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ckev.chooseimagelibrary.R;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.ckev.chooseimagelibrary.base.view.ClickableAdapter;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.List;

/**
 * 选择图片的adapter,item点击的逻辑处理放在外部处理
 * @see com.ckev.chooseimagelibrary.base.img.presenter.ChooseImagePresenterImpl#doOnItemClick(View, int, int, int)
 * Created by ckerv on 16/10/11.
 */
public class ChooseImageAdapter extends ClickableAdapter<ChooseImageAdapter.ChooseImageViewHolder> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    private List<String> mSelectedImage;
    private List<String> mImgPaths;

    public ChooseImageAdapter(List<String> paths, List<String> selectedImage) {
        this.mImgPaths = paths;
        this.mSelectedImage = selectedImage;
    }

    @Override
    public void onBindVH(ChooseImageViewHolder holder, int position) {

        holder.ivImage.setImageResource(R.drawable.base_img_null);
        holder.ivSelectState.setImageResource(R.drawable.choose_image_unselected);

        CommonImageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(mImgPaths.get(position)), holder.ivImage
                , CommonImageLoader.MEMORY_CACHE_OPTIONS);

        final ImageView ivImageView = holder.ivImage;
        final ImageView ivSelect = holder.ivSelectState;
        final String imageUrl = mImgPaths.get(position);

        ivImageView.setColorFilter(null);
        /**
         * setTag,便于外部根据imageUrl拿到相关的holder
         */
        holder.itemView.setTag(imageUrl);
        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(imageUrl)) {
            ivSelect.setImageResource(R.drawable.choose_image_selected);
            ivImageView.setColorFilter(Color.parseColor("#77000000"));
        }
    }

    @Override
    public ChooseImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChooseImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_choose_image, parent, false));
    }

    @Override
    public int getItemCount() {
        return mImgPaths.size();
    }

    public static class ChooseImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public ImageView ivSelectState;

        public ChooseImageViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.choose_img_iv);
            ivSelectState = (ImageView) itemView.findViewById(R.id.choose_img_iv_select_state);
        }
    }
}
