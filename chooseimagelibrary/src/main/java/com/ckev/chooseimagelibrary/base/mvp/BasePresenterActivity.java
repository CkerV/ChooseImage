package com.ckev.chooseimagelibrary.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ckev.chooseimagelibrary.base.activity.BaseActivity;


/**
 * MVP中的基类Activity
 * <P>
 * 1.生命周期与presenter联动
 * 2.onDestory会调用presenter.detachView方法,避免内存泄露
 * Created by ckerv on 16/9/21.
 */
public abstract class BasePresenterActivity<TPresenter extends BasePresenter, TView extends BaseView> extends BaseActivity {

    protected TPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        mPresenter.onCreate();
        mPresenter.attachView(getView());
    }

    protected abstract TView getView();

    protected abstract TPresenter getPresenter();

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
        mPresenter.detachView();
    }


}
