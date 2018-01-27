package com.gamerole.rongtalk.RouteService;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.App;
import com.eqdd.library.Iservice.rongtalk.RongConnectService;
import com.eqdd.library.base.RoutConfig;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by lvzhihao on 17-4-5.
 */
@Route(path = RoutConfig.RONGTALK_SERVICE_CONNECT)
public class RongConnectServiceImpl implements RongConnectService {


    private OnResult onResult;

    @Override
    public void getToken(String token, OnResult onResult) {
        this.onResult = onResult;

        connect(token);
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @param
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(final String token) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            System.out.println(Thread.currentThread().getName());
            if (App.INSTANCE.getApplicationInfo().packageName.equals(RongTalkServiceImpl.getCurProcessName(App.INSTANCE.getApplicationContext()))) {
                RongIM.connect(token, new RongIMClient.ConnectCallback() {
                    /**
                     * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                     * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                     */
                    @Override
                    public void onTokenIncorrect() {
                        onResult.result("token不正确", false);
                    }

                    /**
                     * 连接融云成功
                     *
                     * @param userid 当前 token 对应的用户 id
                     */
                    @Override
                    public void onSuccess(String userid) {
                        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
                        RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
                        onResult.result(token, true);

                    }

                    /**
                     * 连接融云失败
                     *
                     * @param errorCode 错误码，可到官网 查看错误码对应的注释
                     */
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        System.out.println("###############################连接失败，" + errorCode);
                        onResult.result("融云登录失败" + errorCode.toString(), false);
                    }
                });


            }
        });
    }


    @Override
    public void init(Context context) {
    }


}
