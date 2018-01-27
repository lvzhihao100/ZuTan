package com.eqdd.library.Iservice.rongtalk;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.bean.room.User;

/**
 * @author吕志豪 .
 * @date 18-1-26  上午10:36.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public interface RongRefreshService extends IProvider{
    void setCurrentUser(User user);

    void refreshUserCache(User user);

    void refreshGroupCache(Zu zu);
}
