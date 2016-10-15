package com.ckev.chooseimage_test;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ckev.chooseimagelibrary.base.activity.BaseActivity;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedChangeListener;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedFinishedListener;
import com.ckev.chooseimagelibrary.base.img.view.ChooseImageActivity;
import com.ckev.chooseimagelibrary.base.view.ClickableAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity implements  OnImageSelectedFinishedListener, OnImageSelectedChangeListener {

    private final static String TAG = "TestActivity";

    private List<String> mDatas;

    private RecyclerView mRecyclerView;

    private DisplayImageAdapter mAdapter;

    /**
     * 可选择的图片数量
     */
    private int mSelectNum;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initVariables();
        initViews();
        initListener();
    }

    private void initVariables() {
         mSelectNum = 9;
         mDatas = new ArrayList<>();
         mDatas.add("android.resource://com.ckev.chooseimage_test/drawable/chose_add");
    }

    private void initViews() {
         mRecyclerView = (RecyclerView) findViewById(R.id.test_rv_images);
         mAdapter = new DisplayImageAdapter(TestActivity.this, R.layout.item_display_image, mDatas, mSelectNum);
         mRecyclerView.setLayoutManager(new GridLayoutManager(TestActivity.this, 3));
         mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new ClickableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.get(position).equals("android.resource://com.ckev.chooseimage_test/drawable/chose_add")) {
                    ChooseImageActivity.startActivity(TestActivity.this, mSelectNum);
                } else {
                    ImageDetailActivity.startActvitiy(TestActivity.this, mDatas.get(position));
                }
            }
        });

    }


    @Override
    public void onFinish(List<String> selectedImages) {
        for(int i = 0; i < selectedImages.size(); i++) {
            Log.i(TAG, "已完成选择的图片:" + selectedImages.get(i));
        }
        mDatas.addAll(0, selectedImages);
        //处理已选择的图片数量超过可选择图片数量的逻辑
        if(mDatas.size() - 1 >= mSelectNum) {
            for(int i = mDatas.size() - 1; i >= mSelectNum; i--)
                mDatas.remove(i);
        }
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onImageSelectedChange(List<String> selectedImages) {
        for(int i = 0; i < selectedImages.size(); i++) {
            Log.i(TAG, "当前选择的图片:" + selectedImages.get(i));
        }
    }
}
