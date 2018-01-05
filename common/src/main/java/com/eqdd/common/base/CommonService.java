package com.eqdd.common.base;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.bulong.rudeness.RudenessScreenHelper;
import com.eqdd.common.utils.http.LogInterceptor;
import com.eqdd.databind.percent.WindowUtil;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zxy.tiny.Tiny;

import java.util.logging.Level;

import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.OkHttpClient;

/**
 * Created by lvzhihao on 17-5-31.
 */
@Route(path = "/commonservice/application")
public class CommonService implements IProvider {
    @Override
    public void init(Context context) {
        App.INSTANCE.registerActivityLifecycleCallbacks(new BaseActivityLifecycleCallbacks());
        WindowUtil.init(context);
        new RudenessScreenHelper(App.INSTANCE, 630).activate();
        Tiny.getInstance().init(App.INSTANCE);
        ZXingLibrary.initDisplayOpinion(context);
        Stetho.initializeWithDefaults(context);
        ShortcutBadger.applyCount(context, 7);
        initOkgoHttp(context);
    }


    private void initOkgoHttp(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
//        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));
//        builder.addInterceptor(new LogInterceptor());
        builder.addInterceptor(loggingInterceptor);
//---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
//-------------------------------------------------------------------------------------//

        OkGo.getInstance().init(App.INSTANCE)                       //必须调用初始化
                .setOkHttpClient(builder.build())                   //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);
        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);
    }

}
