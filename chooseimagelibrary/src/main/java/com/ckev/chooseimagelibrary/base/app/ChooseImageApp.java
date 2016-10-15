package com.ckev.chooseimagelibrary.base.app;

import android.app.Application;

import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;

/**
 * Created by ckerv on 16/10/13.
 */
public class ChooseImageApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    /**
     * 在application初始化Imageloader
     */
    private void initImageLoader() {
        CommonImageLoader.init(getApplicationContext());
        CommonImageLoader.debug(false);
    }
}
