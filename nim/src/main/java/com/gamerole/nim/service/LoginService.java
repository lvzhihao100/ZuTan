package com.gamerole.nim.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.eqdd.common.utils.SPUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.databind.percent.WindowUtil;
import com.eqdd.library.Iservice.nim.NimLoginService;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.http.DialogCallBack;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.location.LocationProvider;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by lvzhihao on 17-5-31.
 */
@Route(path = RoutConfig.NIM_SERVICE_LOGIN)
public class LoginService implements NimLoginService {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;

    }

    @Override
    public void login(String account, String token, OnResult onResult) {
        LoginInfo info = new LoginInfo("412824199203124753", "22621996279f09c9b8669dff856b7bfd", "d9bdbe17f2daf9de26737d43f8876c24");
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        SPUtil.setParam(Config.USER_ACCOUNT, account);
                        SPUtil.setParam(Config.USER_IM_TOKEN, token);
                        onResult.result(true);
                    }
                    @Override
                    public void onFailed(int code) {
                        System.out.println("错误" + code);
                        onResult.result(false);

                    }

                    @Override
                    public void onException(Throwable exception) {
                        System.out.println("网易错误" + exception.toString());
                        onResult.result(false);

                    }
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }
}
