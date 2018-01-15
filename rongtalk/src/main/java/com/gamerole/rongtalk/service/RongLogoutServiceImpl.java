package com.gamerole.rongtalk.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.library.Iservice.rongtalk.RongLogoutService;
import com.eqdd.library.base.RoutConfig;

import io.rong.imkit.RongIM;

/**
 * Created by lvzhihao on 17-4-5.
 */
@Route(path = RoutConfig.RONGTALK_SERVICE_LOGOUT)
public class RongLogoutServiceImpl implements RongLogoutService {

    private Context context;


    @Override
    public void init(Context context) {

        this.context = context;
    }

    @Override
    public void lougout() {
        RongIM.getInstance().logout();
    }
}
