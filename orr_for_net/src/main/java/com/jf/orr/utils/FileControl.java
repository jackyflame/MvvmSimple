package com.jf.orr.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileControl {

    public String sRoot;
    public String sCacheRoot;
    public String sImageCacheRootPath;
    public String sFileCacheRootPath;

    public static final String IMAGE_CACHE_FOLDER_SUFFIX = "/ImageCache";
    public static final String FILE_CACHE_FOLDER_SUFFIX = "/FileControl";
    private static final String REQUEST_CACHE_FOLDER_SUFFIX = "/HttpCache";

    private static class SingletonHolder {
        /***单例对象实例*/
        static final FileControl INSTANCE = new FileControl();
    }

    public static FileControl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private FileControl() {}

    public void install(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // SD-card available
            sRoot = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Android/data/" + context.getPackageName();
            sCacheRoot = sRoot + "/cache";
            File file = new File(sCacheRoot);
            if (!file.exists()) {
                if (!file.mkdirs())
                    sCacheRoot = context.getCacheDir().getAbsolutePath();
            }
        } else {
            sCacheRoot = context.getCacheDir().getAbsolutePath();
        }

        initImageCacheFile();
        initFileCacheFile();
    }

    public String requestCacheFloderPath() {
        return sFileCacheRootPath + REQUEST_CACHE_FOLDER_SUFFIX;
    }

    private void initImageCacheFile() {
        sImageCacheRootPath = sCacheRoot + IMAGE_CACHE_FOLDER_SUFFIX;
        File file = new File(sImageCacheRootPath);
        if (!file.exists()) {
            if (!file.mkdirs())
                sImageCacheRootPath = sCacheRoot;
        }
    }

    private void initFileCacheFile() {
        sFileCacheRootPath = sCacheRoot + FILE_CACHE_FOLDER_SUFFIX;
        File file = new File(sFileCacheRootPath);
        if (!file.exists()) {
            if (!file.mkdirs())
                sFileCacheRootPath = sCacheRoot;
        }
    }
}
