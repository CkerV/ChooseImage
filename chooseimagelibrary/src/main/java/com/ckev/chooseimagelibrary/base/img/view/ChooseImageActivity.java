package com.ckev.chooseimagelibrary.base.img.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ckev.chooseimagelibrary.R;
import com.ckev.chooseimagelibrary.base.img.assist.ChooseImageManager;
import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageFolderChangeListener;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedChangeListener;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedFinishedListener;
import com.ckev.chooseimagelibrary.base.img.presenter.ChooseImagePresenterImpl;
import com.ckev.chooseimagelibrary.base.mvp.BasePresenter;
import com.ckev.chooseimagelibrary.base.mvp.BasePresenterActivity;
import com.ckev.chooseimagelibrary.base.mvp.BaseView;
import com.ckev.chooseimagelibrary.base.view.ClickableAdapter;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * 选择图片的主Acitvity
 * @see IChooseImageView 的实现类
 * Created by ckerv on 16/10/11.
 */
public class ChooseImageActivity extends BasePresenterActivity implements IChooseImageView, View.OnClickListener,
        ClickableAdapter.OnItemClickListener, OnImageFolderChangeListener {

    private RecyclerView mRecyclerView;
    private RelativeLayout mRlBottomBar;
    private TextView mTvImgsCount;
    private TextView mTvChangeDir;
    /**
     * titlebar 相关
     */
    private RelativeLayout mRlTitle;
    private TextView mTvTitleName;
    private ImageButton mBtnTitilebarBack;
    private TextView mTvFinished;

    //选择模式,分单选和多选
    public static final int SINGLE_CHOICE_MODE = 0X010;
    public static final int MULTI_CHOICE_MODE = 0X020;

    /**
     * 外部启动参数相关
     */
    private static final String SELECT_LIMIT_KEY = "SELECT_LIMIT_NUMBER";
    private int mSelectLimitNum;
    /**
     * 选择模式，默认是多选模式
     */
    private static final String KEY_CHOICE_MODE = "KEY_CHOICE_MODE";
    private int mChoiceMode = MULTI_CHOICE_MODE;

    private ChooseImagePresenterImpl mChooseImagePresenterImpl;
    private ChooseImageAdapter mChooseImageAdapter;
    private ImageFolderPopupWindow mImgFolderPopup;

    /**
     * 此变量用来回调向外暴露的相关接口方法
     * 以弱引用的形式保存启动的Activity,防止内存泄漏
     */
    private static WeakReference<Context> mStartContext;


    /**
     * 启动activity
     *
     * @param context
     * @param selectLimitNum 最多可以选择的数量
     */
    public static void startActivity(Context context, int selectLimitNum) {
        mStartContext = new WeakReference<Context>(context);
        Intent intent = new Intent(context, ChooseImageActivity.class);
        intent.putExtra(SELECT_LIMIT_KEY, selectLimitNum);
        intent.putExtra(KEY_CHOICE_MODE, MULTI_CHOICE_MODE);
        context.startActivity(intent);
    }

    /**
     * 启动activity,选择模式为单选
     * @param context
     */
    public static void startActivityInSingleMode(Context context) {
        mStartContext = new WeakReference<Context>(context);
        Intent intent = new Intent(context, ChooseImageActivity.class);
        intent.putExtra(SELECT_LIMIT_KEY, 1);
        intent.putExtra(KEY_CHOICE_MODE, SINGLE_CHOICE_MODE);
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_choose_image;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initVariables();
        initViews();
        initListener();
        loadImages();
    }


    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.choose_img_rv);
        mRlBottomBar = (RelativeLayout) findViewById(R.id.choose_img_rl_bottom_bar);
        mTvImgsCount = (TextView) findViewById(R.id.choose_img_tv_count);
        mTvChangeDir = (TextView) findViewById(R.id.choose_img_tv_dir);
        mRlTitle = (RelativeLayout) findViewById(R.id.choose_img_rv_title_bar);
        mTvTitleName = (TextView) mRlTitle.findViewById(R.id.titlebar_tv_title);
        mBtnTitilebarBack = (ImageButton) mRlTitle.findViewById(R.id.titlebar_btn_back);
        mTvFinished = (TextView) mRlTitle.findViewById(R.id.titlebar_tv_right);
        initTitleBar();
    }

    private void initListener() {
        mRlBottomBar.setOnClickListener(this);
        mBtnTitilebarBack.setOnClickListener(this);
        mTvFinished.setOnClickListener(this);
    }


    private void initTitleBar() {
        mBtnTitilebarBack.setVisibility(View.VISIBLE);
        mTvFinished.setText("完成");
        mTvTitleName.setText("选择图片");
    }

    private void initVariables() {
        Intent intent = getIntent();
        /**
         * 判断启动的Activity是否为OnImageSelectedFinishedListener的实现类,如果不是,就抛出异常
         */
        if (mStartContext.get() instanceof OnImageSelectedFinishedListener) {

        } else {
            throw new IllegalArgumentException("The Activity should implement OnImageSelectedFinishedListener!!!");
        }
        mSelectLimitNum = intent.getIntExtra(SELECT_LIMIT_KEY, 0);
        mChoiceMode = intent.getIntExtra(KEY_CHOICE_MODE, MULTI_CHOICE_MODE);
        mChooseImagePresenterImpl = new ChooseImagePresenterImpl(ChooseImageActivity.this, ChooseImageActivity.this);
    }

    private void loadImages() {
        mChooseImagePresenterImpl.loadImages();
    }

    private void changeImageFolder(ImageFolderBean mCurrentFolder) {
        mChooseImagePresenterImpl.doOnChangeImageFolder(mCurrentFolder);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.choose_img_rl_bottom_bar) {
            showChooseImgFolderPopupwindow();

        } else if (i == R.id.titlebar_btn_back) {
            mChooseImagePresenterImpl.chooseBack();
            finish();

        } else if (i == R.id.titlebar_tv_right) {
            mTvFinished.setClickable(false);
            if (mStartContext.get() != null) {
                mChooseImagePresenterImpl.chooseCompleted((OnImageSelectedFinishedListener) mStartContext.get());
            }
            finish();

        } else {
        }
    }

    /**
     * 创建和初始化文件夹窗口
     */
    private void initChooseImgFolderPopupwindow() {
        mImgFolderPopup = new ImageFolderPopupWindow(
                LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.popup_choose_img_folderlist, null)
                , ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (getResources().getDisplayMetrics().heightPixels * 0.7)
                , R.id.choose_img_rv_folder
                , true
                , ChooseImageManager.getInstance().getImageFolders());

        mImgFolderPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                //还原背景颜色
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

        mImgFolderPopup.setOnImageFolderChangeListener(this);
    }

    /**
     * 打开图片文件夹窗口
     */
    private void showChooseImgFolderPopupwindow() {
        if (mImgFolderPopup == null) {
            initChooseImgFolderPopupwindow();
        }
        mImgFolderPopup.setAnimationStyle(R.style.anim_choose_img_popup_folders);
        mImgFolderPopup.showAsDropDown(mRlBottomBar, 0, 0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .3f;
        getWindow().setAttributes(lp);
    }


    @Override
    public void onItemClick(View view, int position) {
        mChooseImagePresenterImpl.doOnItemClick(view, position, mChoiceMode, mSelectLimitNum);
        /**
         * 判断mStartContext有没有实现OnImageSelectedChangeListener,如果有,就回调相关方法
         */
        if (mStartContext.get() instanceof OnImageSelectedChangeListener) {
            mChooseImagePresenterImpl.chooseChanged((OnImageSelectedChangeListener) mStartContext.get());
        }
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mChooseImagePresenterImpl;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void initRecyclerView(List<String> allImages, List<String> selectedImages) {
        mChooseImageAdapter = new ChooseImageAdapter(allImages, selectedImages);
        mChooseImageAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ChooseImageActivity.this, 3));
        mRecyclerView.setAdapter(mChooseImageAdapter);
    }


    @Override
    public void setImagesCountText(int imagesCount) {
        mTvImgsCount.setText(imagesCount + "张");
    }

    @Override
    public void changeItemBackground(String imageUrl, boolean selected) {
        View itemView = mRecyclerView.findViewWithTag(imageUrl);
        ChooseImageAdapter.ChooseImageViewHolder holder = new ChooseImageAdapter.ChooseImageViewHolder(itemView);
        if (selected) {
            holder.ivSelectState.setImageResource(R.drawable.choose_image_selected);
            holder.ivImage.setColorFilter(Color.parseColor("#77000000"));
        } else {
            holder.ivSelectState.setImageResource(R.drawable.choose_image_unselected);
            holder.ivImage.setColorFilter(null);
        }
    }

    @Override
    public void refreshAfterChangeImageFolder(ImageFolderBean mCurrentFolder) {
        mTvImgsCount.setText(mCurrentFolder.getCount() + "张");
        mTvChangeDir.setText(mCurrentFolder.getName());
        mImgFolderPopup.dismiss();
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onImageFolderChange(ImageFolderBean currentFolder) {
        changeImageFolder(currentFolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mStartContext != null) {
            mStartContext.clear();
            mStartContext = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            mChooseImagePresenterImpl.chooseBack();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
