package com.ckev.chooseimagelibrary.base.img.assist;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

/**
 * 封装ImageLoader,统一配置管理
 * Created by ckerv on 16/10/10.
 */
public class CommonImageLoader {

    /**
     * 无缓存配置
     */
    public static final DisplayImageOptions NO_CACHE_OPTIONS = new DisplayImageOptions.Builder()
            // 设置图片在下载期间显示的图片
            //.showImageOnLoading(R.mipmap.img_null)
            // 设置图片Uri为空或是错误的时候显示的图片
            //.showImageForEmptyUri(R.mipmap.base_img_null)
            // 设置图片加载/解码过程中错误时候显示的图片
            //.showImageOnFail(R.mipmap.base_img_null)
            // 设置图片以如何的编码方式显示
            .imageScaleType(ImageScaleType.EXACTLY)
            .considerExifParams(true)
            // 设置图片的解码类型
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    /**
     * 内存缓存配置
     */
    public static final DisplayImageOptions MEMORY_CACHE_OPTIONS = new DisplayImageOptions.Builder()
            // 设置下载的图片是否缓存在内存中
            .cacheInMemory(true)
            // 设置图片以如何的编码方式显示
            .imageScaleType(ImageScaleType.EXACTLY)
            .considerExifParams(true)
            // 设置图片的解码类型
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    /**
     * 双缓存配置
     */
    public static final DisplayImageOptions DOUBLE_CACHE_OPTIONS = new DisplayImageOptions.Builder()
            // 设置下载的图片是否缓存在内存中
            .cacheInMemory(true)
            // 设置下载的图片是否缓存在SD中
            .cacheOnDisk(true)
            // 设置图片以如何的编码方式显示
            .imageScaleType(ImageScaleType.EXACTLY)
            .considerExifParams(true)
            // 设置图片的解码类型
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    /**
     * 在application里面进行初始化，防止内存泄漏
     *
     * @param context
     */
    public static void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                // .memoryCacheSize(2 * 1024 * 1024)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static void debug(boolean debug) {
        L.writeLogs(debug);
        L.writeDebugLogs(debug);
    }

    /**
     * 在一个imageView里面异步展示一个图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions op) {
        ImageLoader.getInstance().displayImage(uri, imageView, op);
    }

    /**
     * 在一个imageView里面异步展示一个图片，监听加载过程
     *
     * @param uri
     * @param imageView
     */
    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions op, SimpleImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(uri, imageView, op, listener);
    }

    /**
     * 在一个imageView里面异步展示一个图片，监听加载过程，监听加载进度条
     *
     * @param uri
     * @param imageView
     */
    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions op, SimpleImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        ImageLoader.getInstance().displayImage(uri, imageView, op, listener, progressListener);
    }

    /**
     * 异步加载一个图片，监听加载过程，指定大小，在回调中取得位图
     * 可以用来加载大图。
     *
     * @param uri
     * @param imageSize
     * @param listener
     */
    public static void loadImage(String uri, ImageSize imageSize, DisplayImageOptions op, ImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(uri, imageSize, op, listener);
    }

    /**
     * 同步加载一个图片，获得位图
     *
     * @param uri
     * @return
     */
    public static Bitmap loadImageSync(String uri) {
        return loadImageSync(uri, null, NO_CACHE_OPTIONS);
    }

    /**
     * 同步加载一个图片，获得位图，指定大小
     *
     * @param uri
     * @return
     */
    public static Bitmap loadImageSync(String uri, ImageSize targetImageSize, DisplayImageOptions op) {
        return ImageLoader.getInstance().loadImageSync(uri, targetImageSize, op);
    }

    /**
     * 获取磁盘缓存
     *
     * @return
     */
    public static DiskCache getDiskCache() {
        return ImageLoader.getInstance().getDiskCache();
    }

    /**
     * 获取内存缓存
     *
     * @return
     */
    public static MemoryCache getMemoryCache() {
        return ImageLoader.getInstance().getMemoryCache();
    }

    /**
     * 清除硬盘缓存
     */
    public static void cleanDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 清除内存缓存
     */
    public static void cleanMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 内存和硬盘双清
     */
    public static void cleanDoubleCache() {
        cleanDiskCache();
        cleanMemoryCache();
    }
}
