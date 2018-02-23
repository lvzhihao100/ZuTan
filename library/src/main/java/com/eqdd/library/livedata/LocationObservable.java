package com.eqdd.library.livedata;

import android.support.annotation.MainThread;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.eqdd.common.base.App;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * @author吕志豪 .
 * @date 17-12-15  下午3:30.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class LocationObservable extends Observable<AMapLocation> {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private static LocationObservable sInstance;
    private static long time = 1000 * 60 * 5;

    private Listener listener;

    final class Listener extends MainThreadDisposable implements AMapLocationListener {

        private final Observer<? super AMapLocation> observer;

        Listener(Observer<? super AMapLocation> observer) {
            this.observer = observer;
            locationOption = new AMapLocationClientOption();
        }

        @Override
        protected void onDispose() {
            destroyLocation();
        }

        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                if (!isDisposed()) {
                    observer.onNext(loc);
                }
            }
        }

    }

    public LocationObservable() {
    }


    @Override
    protected void subscribeActual(Observer<? super AMapLocation> observer) {
        listener = new Listener(observer);
        observer.onSubscribe(listener);
        initLocation();
        startLocation();
    }

    public static void setTime(long time) {
        LocationObservable.time = time;
    }

    @MainThread
    public static LocationObservable get() {
        if (sInstance == null) {
            sInstance = new LocationObservable();
        }
        return sInstance;
    }

    /**
     * 初始化定位
     *
     * @author
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(App.INSTANCE);
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(listener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(1000 * 60 * 5);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        locationOption.setInterval(time);
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */

            listener = null;
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}