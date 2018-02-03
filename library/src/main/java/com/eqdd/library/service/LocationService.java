package com.eqdd.library.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.eqdd.common.base.App;
import com.eqdd.library.livedata.LocationObservable;
import com.eqdd.library.utils.HttpUtil;

/**
 * @author吕志豪 .
 * @date 18-1-27  上午10:43.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com/adb
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new LocationObservable().subscribe(aMapLocation -> {
            if (aMapLocation.getErrorCode() == 0) {
                HttpUtil.updateLocation(aMapLocation, aMapLocation.getProvince(), aMapLocation.getCity(), aMapLocation.getDistrict(), aMapLocation.getStreet());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 在API16之后，可以使用build()来进行Notification的构建 Notification
        Notification notification = new Notification.Builder(App.INSTANCE)
//                .setContentText("族探运行中")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setWhen(System.currentTimeMillis())
                .build();
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, notification);// 开始前台服务
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        super.onDestroy();
    }
}
