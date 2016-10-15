package com.ckev.chooseimagelibrary.base.img.listener;

import java.util.List;

/**
 * 选择图片改变时的回调接口
 * Created by ckerv on 16/10/11.
 */
public interface OnImageSelectedChangeListener {
    void onImageSelectedChange(List<String> selectedImages);
}
