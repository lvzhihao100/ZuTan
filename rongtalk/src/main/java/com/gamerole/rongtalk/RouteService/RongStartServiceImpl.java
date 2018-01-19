package com.gamerole.rongtalk.RouteService;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.library.Iservice.rongtalk.RongStartService;
import com.eqdd.library.base.RoutConfig;

import io.rong.imkit.RongIM;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.UserInfo;

/**
 * @author吕志豪 .
 * @date 18-1-17  上午11:10.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.RONGTALK_SERVICE_START)
public class RongStartServiceImpl implements RongStartService {
    @Override
    public void startPrivate(Context context, String guid, String title, String uri) {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(guid, title, Uri.parse(uri)));
        RongUserInfoManager.getInstance().setUserInfo(new UserInfo(guid, title, Uri.parse(uri)));
        RongIM.getInstance().startPrivateChat(context, guid, title);
    }


    @Override
    public void startGroup(Context context, String guid, String title) {
        RongIM.getInstance().startGroupChat(context, guid, title);

    }

    @Override
    public void init(Context context) {

    }
}
