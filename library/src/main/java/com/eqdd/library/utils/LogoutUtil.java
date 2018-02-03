package com.eqdd.library.utils;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.App;
import com.eqdd.common.base.AppManager;
import com.eqdd.common.utils.SPUtil;
import com.eqdd.library.Iservice.rongtalk.RongLogoutService;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.service.LocationService;

import cn.jpush.android.api.JPushInterface;

/**
 * @author吕志豪 .
 * @date 18-1-13  下午4:50.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class LogoutUtil {

    public static void logout() {
        RongLogoutService rongLogoutService = ARouter.getInstance().navigation(RongLogoutService.class);
        rongLogoutService.lougout();
        JPushInterface.clearLocalNotifications(App.INSTANCE);
        JPushInterface.deleteAlias(App.INSTANCE, 0);
        JPushInterface.cleanTags(App.INSTANCE, 0);
        SPUtil.setParam(Config.IDCARD, "");
        AppManager.getAppManager().finishAllActivity();
        App.INSTANCE.stopService(new Intent(App.INSTANCE, LocationService.class));
        ARouter.getInstance().build(RoutConfig.APP_LOGIN).navigation();
    }
}
