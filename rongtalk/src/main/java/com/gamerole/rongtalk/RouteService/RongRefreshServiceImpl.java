package com.gamerole.rongtalk.RouteService;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.library.Iservice.rongtalk.RongRefreshService;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.bean.room.User;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * @author吕志豪 .
 * @date 18-1-26  上午10:36.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.RONGTALK_SERVICE_REFRESH)
public class RongRefreshServiceImpl implements RongRefreshService {
    @Override
    public void init(Context context) {

    }

    @Override
    public void setCurrentUser(User user) {
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(user.getId() + "", user.getName(), Uri.parse(user.getCatongImg())));
    }

    @Override
    public void refreshUserCache(User user) {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getId() + "", user.getName(), Uri.parse(user.getCatongImg())));
    }

    @Override
    public void refreshGroupCache(Zu zu) {
        RongIM.getInstance().refreshGroupInfoCache(new Group(zu.getId() + "", zu.getName(), Uri.parse(zu.getLogo())));
    }
}
