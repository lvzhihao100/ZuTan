package com.eqdd.library.Iservice.rongtalk;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author吕志豪 .
 * @date 18-1-13  下午5:00.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 * 融云连接服务器
 */

public interface RongConnectService extends IProvider {
    void getToken(String token, OnResult onResult);

    public interface OnResult {
        public void result(String token, boolean isSuccess);
    }
}
