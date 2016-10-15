package com.ckev.chooseimagelibrary.base.img.presenter;

import android.view.View;

import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedFinishedListener;


/**
 * 选择图片Presenter的接口类,定义相关逻辑处理方法
 * Created by ckerv on 16/10/11.
 */
public interface IChooseImagePresenter  {
    /**
     * 加载图片
     */
    void loadImages();

    /**
     * item点击时的逻辑处理
     * @param view
     * @param position
     * @param choiceMode
     * @param selectLimitNum
     */
    void doOnItemClick(View view, int position, int choiceMode, int selectLimitNum);

    /**
     * 文件夹改变时的逻辑处理
     * @param imageFolderBean
     */
    void doOnChangeImageFolder(ImageFolderBean imageFolderBean);

    /**
     * 完成图片选择时的逻辑处理
     * @param onImageSelectedFinishedListener 向外部暴露的回调接口,由外部进行实现,内部直接调用其回调方法
     */
    void chooseCompleted(OnImageSelectedFinishedListener onImageSelectedFinishedListener);

    /**
     * 选择图片返回
     */
    void chooseBack();
}
