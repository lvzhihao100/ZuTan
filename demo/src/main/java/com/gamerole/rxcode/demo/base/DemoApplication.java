package com.gamerole.rxcode.demo.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.App;
import com.eqdd.common.base.CommonService;
import com.eqdd.library.service.LibraryService;
import com.gamerole.rxcode.demo.db.DBHelper;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:20.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class DemoApplication extends Application {

    @Autowired
    LibraryService libraryService;
    @Autowired
    CommonService commonService;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isApkInDebug(this)) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(App.INSTANCE); // 尽可能早，推荐在Application中初始化
        ARouter.getInstance().inject(this);
        libraryService.init(this);
        commonService.init(this);
        DBHelper.getInstance().init(this);
    }

    public boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
