package com.ckev.chooseimagelibrary.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 希望所有Activity能继承此基类,简化操作
 * Created by ckerv on 16/9/21.
 *
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        init(savedInstanceState);
    }

    /**
     * 提供布局Id
     * @return
     */
    protected abstract int provideLayoutId();

    /**
     * 初始化操作
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
