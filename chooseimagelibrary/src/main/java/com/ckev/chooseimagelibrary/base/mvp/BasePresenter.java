package com.ckev.chooseimagelibrary.base.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * MVP基类Presenter,与Acitvity生命周期联动
 * Created by ckerv on 16/9/21.
 */
public class BasePresenter<TView extends BaseView> {

    protected Reference<TView> mViewRef;

    public void attachView(TView view) {
        mViewRef = new WeakReference<TView>(view);
    }

    protected TView getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if(mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected void onCreate() {

    }

    protected void onResume() {
    }

    protected void onPause() {
    }

    protected void onDestory() {
    }

}
