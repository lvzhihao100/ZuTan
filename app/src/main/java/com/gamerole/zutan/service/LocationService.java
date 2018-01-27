package com.gamerole.zutan.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.eqdd.library.utils.HttpUtil;
import com.gamerole.zutan.livedata.LocationObservable;

/**
 * @author吕志豪 .
 * @date 18-1-27  上午10:43.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
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
        new LocationObservable().subscribe(amapLocation -> {
            if (amapLocation.getErrorCode() == 0) {
                HttpUtil.updateLocation(amapLocation,amapLocation.getProvince(),amapLocation.getCity(),amapLocation.getDistrict(),amapLocation.getStreet());
            }
        });
    }


}
