package com.eqdd.library.utils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.AppManager;
import com.eqdd.library.Iservice.rongtalk.RongLogoutService;
import com.eqdd.library.base.RoutConfig;

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
        AppManager.getAppManager().finishAllActivity();
        ARouter.getInstance().build(RoutConfig.APP_LOGIN).navigation();
    }
}
