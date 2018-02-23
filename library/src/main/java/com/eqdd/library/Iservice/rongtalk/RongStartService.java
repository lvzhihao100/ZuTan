package com.eqdd.library.Iservice.rongtalk;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author吕志豪 .
 * @date 18-1-17  上午11:07.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 * 融云开启聊天
 */

public interface RongStartService extends IProvider{
    public  void startPrivate(Context context, String guid, String title, String uri);



    public  void startGroup(Context context, String guid, String title);

    void enterWallet(Activity activity);
}
