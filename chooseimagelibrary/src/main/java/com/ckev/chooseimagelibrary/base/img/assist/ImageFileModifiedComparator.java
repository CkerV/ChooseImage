package com.ckev.chooseimagelibrary.base.img.assist;

import java.io.File;
import java.util.Comparator;

/**
 * 对全路径的图片进行按修改时间的排序
 * Created by ckerv on 16/10/11.
 */
public class ImageFileModifiedComparator implements Comparator<String> {

    @Override
    public int compare(String lhs, String rhs) {
        if (new File(lhs).lastModified() < new File(rhs).lastModified()) {
            return 1;// 最后修改的照片在前
        } else if (new File(lhs).lastModified() > new File(rhs).lastModified()) {
            return -1;
        } else {
            return 0;
        }
    }
}
