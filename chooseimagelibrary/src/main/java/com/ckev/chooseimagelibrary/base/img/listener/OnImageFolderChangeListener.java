package com.ckev.chooseimagelibrary.base.img.listener;


import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;

/**
 * 改变图片文件夹时的回调接口
 * Created by ckerv on 16/10/11.
 */
public interface OnImageFolderChangeListener {
    void onImageFolderChange(ImageFolderBean currentFolder);
}

