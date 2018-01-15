package com.gamerole.rongtalk.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.eqdd.common.base.AppManager;
import com.eqdd.library.utils.LogoutUtil;
import com.gamerole.rongtalk.service.RongConnectServiceImpl;

import io.rong.imlib.RongIMClient;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by lvzhihao on 17-4-6.
 */

public class ConnectionStatusListener implements RongIMClient.ConnectionStatusListener {
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus) {

            case CONNECTED://连接成功。

                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。

                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                System.out.println("用户账户在其他设备登录，本机会被踢掉线");
                AndroidSchedulers.mainThread().createWorker().schedule(() -> {
                    Activity activity = AppManager.getAppManager().currentActivity();
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("用户账户在其他设备登录");
                    builder.setTitle("下线通知");
                    builder.setCancelable(false);
                    builder.setPositiveButton("重新登陆", (dialog, which) -> {
                        dialog.dismiss();
                        activity.startService(new Intent(activity, RongConnectServiceImpl.class));

                    });
                    builder.setNegativeButton("退出", (DialogInterface dialog, int which) -> {
                                dialog.dismiss();
                                LogoutUtil.logout();
                            }
                    );
                    builder.create().show();
                });

                break;
        }
    }
}