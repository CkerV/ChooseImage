package com.ckev.chooseimagelibrary.base.img.assist;


import com.ckev.chooseimagelibrary.base.img.bean.ImageFolderBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 此类作用是对选择图片中的变量进行统一管理,亦是MVP中的Model层
 * Created by ckerv on 16/10/11.
 */
public class ChooseImageManager {

    private static ChooseImageManager sChooseImageManager;

    /**
     * 所有图片
     */
    private List<String> allImages;

    /**
     * 所有图片文件夹
     */
    private List<ImageFolderBean> imageFolders;

    /**
     * 用户选择的图片
     */
    private List<String> selectedImages;

    /**
     * 图片总数
     */
    private int imagesCount;


    private ChooseImageManager() {
        allImages = new ArrayList<String>();
        imageFolders = new ArrayList<ImageFolderBean>();
        selectedImages = new LinkedList<>();
    }

    public static ChooseImageManager getInstance() {
        if(sChooseImageManager == null) {
            if (sChooseImageManager == null) {
                synchronized (ChooseImageManager.class) {
                    sChooseImageManager = new ChooseImageManager();
                }
            }
        }
        return sChooseImageManager;
    }

    public List<String> getAllImages() {
        return allImages;
    }

    public void setAllImages(List<String> allImages) {
        this.allImages = allImages;
    }

    public List<ImageFolderBean> getImageFolders() {
        return imageFolders;
    }

    public void setImageFolders(List<ImageFolderBean> imageFolders) {
        this.imageFolders = imageFolders;
    }

    public List<String> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(List<String> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    /**
     * 完成选择,清除所有数据
     */
    public void chooseCompleted() {
        clearAllList();
    }

    public void clearAllList() {
        clearAllImages();
        clearImageFolders();
        clearSelectedImages();
    }

    public void clearAllImages() {
        allImages.clear();
    }

    public void clearImageFolders() {
        imageFolders.clear();
    }

    public void clearSelectedImages() {
        selectedImages.clear();
    }
}
