package com.gamerole.zutan.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.App;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.utils.SPUtil;
import com.eqdd.library.Iservice.rongtalk.RongConnectService;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.gamerole.zutan.R;
import com.gamerole.zutan.databinding.AppActivitySplashBinding;
import com.eqdd.library.service.LocationService;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;

/**
 * @author吕志豪 .
 * @date 18-1-27  上午8:56.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class SplashActivity extends CommonActivity {

    private AppActivitySplashBinding dataBinding;
    @Autowired
    RongConnectService rongConnectService;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_splash);
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        if (SPUtil.getParam(Config.GUIDE_FIRST, true)) {
            ARouter.getInstance().build(RoutConfig.APP_GUIDE).navigation();
            finish();
        } else if (TextUtils.isEmpty(SPUtil.getParam(Config.IDCARD, ""))) {
            ARouter.getInstance().build(RoutConfig.APP_LOGIN).navigation();
            finish();
        } else {
            DBUtil.getUserStatic(user -> rongConnectService.getToken(user.getToken(), (token1, isSuccess) -> {
                if (isSuccess) {
                    user.setToken(token1);
                    DBUtil.insertUser(user);
                    SPUtil.setParam(Config.IDCARD, user.getIdCard());
                    JPushInterface.setAlias(App.INSTANCE, 0, user.getId() + "");
//                    if (user.getZuId() != 0) {
//                        HashSet<String> tags = new HashSet<>();
//                        tags.add(user.getZuId() + "");
//                        JPushInterface.setTags(App.INSTANCE, 1, tags);
//                    }
                    startService(new Intent(this,LocationService.class));
                    ARouter.getInstance().build(RoutConfig.APP_HOME).navigation();
                    finish();
                }
            }));
        }
    }

    @Override
    public void setView() {

    }
}
