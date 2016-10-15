package com.ckev.chooseimagelibrary.base.img.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.ckev.chooseimagelibrary.base.img.assist.ChooseImageManager;
import com.ckev.chooseimagelibrary.base.img.assist.ImageFileFilter;
import com.ckev.chooseimagelibrary.base.img.assist.ImageScanUtil;
import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedChangeListener;
import com.ckev.chooseimagelibrary.base.img.listener.OnImageSelectedFinishedListener;
import com.ckev.chooseimagelibrary.base.img.view.ChooseImageActivity;
import com.ckev.chooseimagelibrary.base.img.view.IChooseImageView;
import com.ckev.chooseimagelibrary.base.mvp.BasePresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @see IChooseImagePresenter 的实现类,实现了相应的逻辑处理方法
 * Created by ckerv on 16/10/11.
 */
public class ChooseImagePresenterImpl extends BasePresenter implements IChooseImagePresenter {

    private IChooseImageView mChooseImageView;

    private ChooseImageManager mChooseImageManager;

    private Context mContext;


    public ChooseImagePresenterImpl(IChooseImageView chooseImageView, Context context) {
        this.mChooseImageView = chooseImageView;
        this.mContext = context;
        mChooseImageManager = ChooseImageManager.getInstance();
    }

    /**
     * 采用异步任务加载手机中的图片
     */
    @Override
    public void loadImages() {
        new AsyncTask<String, Integer, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                Integer imagesCount = ImageScanUtil.scanAll(mContext, mChooseImageManager.getAllImages(), mChooseImageManager.getImageFolders());
                return imagesCount;
            }

            @Override
            protected void onPostExecute(Integer imagesCount) {
                super.onPostExecute(imagesCount);
                //保存总图片数量
                mChooseImageManager.setImagesCount(imagesCount);
                /**
                 * 防止Activity退出造成NullPointException
                 */
                if(mChooseImageView.getRecyclerView() != null) {
                    //初始化RecyclerView
                    mChooseImageView.initRecyclerView(mChooseImageManager.getAllImages(), mChooseImageManager.getSelectedImages());
                    mChooseImageView.setImagesCountText(mChooseImageManager.getImagesCount());
                }
            }
        }.execute();
    }

    /**
     * 根据不同的Item状态和选择模式进行逻辑处理
     * @param view
     * @param position
     * @param choiceMode
     * @param selectLimitNum
     */
    @Override
    public void doOnItemClick(View view, int position, int choiceMode, int selectLimitNum) {
        final String imageUrl = mChooseImageManager.getAllImages().get(position);
        if(mChooseImageManager.getSelectedImages().contains(imageUrl)) {
            mChooseImageManager.getSelectedImages().remove(imageUrl);
            mChooseImageView.changeItemBackground(imageUrl, false);
        } else {
            switch (choiceMode) {
                case ChooseImageActivity.MULTI_CHOICE_MODE :
                    if (mChooseImageManager.getSelectedImages().size() == selectLimitNum) {
                        Toast.makeText(mContext, "最多选择" + selectLimitNum + "张图片", Toast.LENGTH_SHORT).show();
                    } else {
                        mChooseImageManager.getSelectedImages().add(imageUrl);
                        mChooseImageView.changeItemBackground(imageUrl, true);
                    }
                    break;
                case ChooseImageActivity.SINGLE_CHOICE_MODE :
                    /**
                     * 可能会存在selectedImages中已经有元素的情况,必须先清除再添加
                     */
                    if(mChooseImageManager.getSelectedImages().size() != 0) {
                        mChooseImageView.changeItemBackground(mChooseImageManager.getSelectedImages().get(0), false);
                    }
                    mChooseImageManager.clearSelectedImages();
                    mChooseImageManager.getSelectedImages().add(imageUrl);
                    mChooseImageView.changeItemBackground(imageUrl, true);
            }
        }
    }

    /**
     * 文件夹改变时的逻辑处理,通过入参的各属性改变RecyclerViewAdapter的数据源
     * @param currentImageFolder
     */
    @Override
    public void doOnChangeImageFolder(ImageFolderBean currentImageFolder) {
        mChooseImageManager.clearAllImages();
        File imgDir = new File(currentImageFolder.getDir());
        String[] imgsName = imgDir.list(new ImageFileFilter());
        for(int i = 0; i < imgsName.length; i++) {
            mChooseImageManager.getAllImages().add(currentImageFolder.getDir() + "/" + imgsName[i]);
        }
        Collections.sort(mChooseImageManager.getAllImages());
        mChooseImageManager.setImagesCount(currentImageFolder.getCount());
        mChooseImageView.refreshAfterChangeImageFolder(currentImageFolder);
        mChooseImageView.notifyAdapterDataSetChanged();
    }

    /**
     * 选择图片改变时的逻辑处理,通过向外暴露的接口直接调用其回调方法
     * @param onImageSelectedChangeListener
     */
    public void chooseChanged(OnImageSelectedChangeListener onImageSelectedChangeListener) {
        onImageSelectedChangeListener.onImageSelectedChange(mChooseImageManager.getSelectedImages());
    }

    /**
     * 选择图片完成时的逻辑处理,通过向外暴露的接口直接调用其毁掉方法
     * @param onImageSelectedFinishedListener 向外部暴露的回调接口,由外部进行实现,内部直接调用其回调方法
     */
    @Override
    public void chooseCompleted(OnImageSelectedFinishedListener onImageSelectedFinishedListener) {
        ArrayList<String> newSelectedImages = new ArrayList<>();
        newSelectedImages.addAll(mChooseImageManager.getSelectedImages());
        onImageSelectedFinishedListener.onFinish(newSelectedImages);
        mChooseImageManager.clearAllList();//清除所有数据
    }

    /**
     * 选择的图片返回(按下TitleBar的返回键),清除所有数据
     */
    @Override
    public void chooseBack() {
        mChooseImageManager.clearAllList();
    }

}
