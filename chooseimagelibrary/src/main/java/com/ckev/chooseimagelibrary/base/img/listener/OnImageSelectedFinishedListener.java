package com.ckev.chooseimagelibrary.base.img.listener;

import java.util.List;

/**
 * 完成选择图片时的回调接口
 * Created by ckerv on 16/10/11.
 */
public interface OnImageSelectedFinishedListener {
    void onFinish(List<String> selectedImages);
}
