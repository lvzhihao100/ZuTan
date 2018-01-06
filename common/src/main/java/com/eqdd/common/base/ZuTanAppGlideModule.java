package com.eqdd.common.base;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author吕志豪 .
 * @date 18-1-6  下午1:43.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@GlideModule
public class ZuTanAppGlideModule extends AppGlideModule {

    private static final int M = 1024 * 1024;
    private static final int MAX_DISK_CACHE_SIZE = 256 * M;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(20 * M));
        //设置磁盘缓存目录及大小
        final String cachedDirName = "glide";
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context,
                cachedDirName, MAX_DISK_CACHE_SIZE));
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .disallowHardwareConfig());
    }
}
