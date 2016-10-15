package com.ckev.chooseimagelibrary.base.img.view;

import android.support.v7.widget.RecyclerView;

import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;
import com.ckev.chooseimagelibrary.base.mvp.BaseView;

import java.util.List;

/**
 * 选择图片的view接口,定义相关的界面处理方法
 * Created by ckerv on 16/10/11.
 */
public interface IChooseImageView extends BaseView {

    /**
     * 增加此方法是为了暴露给p层进行逻辑处理时的判空
     * @return
     */
    RecyclerView getRecyclerView();

    /**
     * 初始化RecyclerView
     * @param allImages
     * @param selectedImages
     */
    void initRecyclerView(List<String> allImages, List<String> selectedImages);

    /**
     * 设置图片张数
     * @param imagesCount
     */
    void setImagesCountText(int imagesCount);

    /**
     * item点击时改变item背景
     * @param imageUrl
     * @param selected
     */
    void changeItemBackground(String imageUrl, boolean selected);

    /**
     * 文件夹改变时刷新界面
     * @param imageFolderBean
     */
    void refreshAfterChangeImageFolder(ImageFolderBean imageFolderBean);

    /**
     * 通知adapter刷新数据源
     */
    void notifyAdapterDataSetChanged();
}
