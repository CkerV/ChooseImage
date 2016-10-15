package com.ckev.chooseimagelibrary.base.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 弹窗基类
 * Created by ckerv on 16/10/11.
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected View mContentView;

    public BasePopupWindow() {
        super();
    }

    public BasePopupWindow(Context context) {
        super(context);
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public BasePopupWindow(View contentView) {
        super(contentView);
    }


    public BasePopupWindow(int width, int height) {
        super(width, height);
    }


    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        this.mContentView = contentView;
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        setBackgroundDrawable(new ColorDrawable());
        init();
    }

    public abstract void init();

    public View findViewById(int resId) {
        return mContentView.findViewById(resId);
    }
}
