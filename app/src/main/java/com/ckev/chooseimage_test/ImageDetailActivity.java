package com.ckev.chooseimage_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.ckev.chooseimagelibrary.base.activity.BaseActivity;
import com.ckev.chooseimagelibrary.base.img.assist.CommonImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

/**
 * 展示图片详情的activity
 * Created by ckerv on 16/10/15.
 */
public class ImageDetailActivity extends BaseActivity {

    private ImageView mIvDisplay;

    private final static String CLICKED_IMAGE_URL = "clicked.image.url";

    private String mImageUrlStr;

    public static void startActvitiy(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(CLICKED_IMAGE_URL, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initVariables();
        initViews();
    }

    private void initVariables() {
        Intent intent = getIntent();
        mImageUrlStr = intent.getStringExtra(CLICKED_IMAGE_URL);
    }

    private void initViews() {
        mIvDisplay = (ImageView) findViewById(R.id.detail_iv_image);
        CommonImageLoader.displayImage(ImageDownloader.Scheme.FILE.wrap(mImageUrlStr), mIvDisplay, CommonImageLoader.NO_CACHE_OPTIONS);
    }
}
