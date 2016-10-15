package com.ckev.chooseimagelibrary.base.img.assist;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 过滤出所有图片文件
 * Created by ckerv on 16/10/11.
 */
public class ImageFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".png")
                || filename.endsWith(".jpeg") || filename.endsWith(".JPEG")
                || filename.endsWith(".JPG"))
            return true;
        return false;
    }
}
