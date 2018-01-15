package com.gamerole.rongtalk.service;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.library.Iservice.rongtalk.RongConnectService;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.library.http.JsonCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by lvzhihao on 17-4-5.
 */
@Route(path = RoutConfig.RONGTALK_SERVICE_CONNECT)
public class RongConnectServiceImpl implements RongConnectService {
    private User user;
    private String token;

    private Context context;

    @Override
    public void getToken() {
        user = DBUtil.getUser().getValue();
        if (user != null) {
            if (!TextUtils.isEmpty(user.getToken())) {
                System.out.println("使用本地token" + token);
                RongIM.getInstance().disconnect();
                connect(token);
            } else {
                System.out.println("获取服务器token中。。。");
                OkGo.<HttpResult<String>>post(HttpConfig.BASE_URL + HttpConfig.GET_RONG_TOKEN)
                        .execute(new JsonCallBack<HttpResult<String>>() {
                            @Override
                            public void onSuccess(Response<HttpResult<String>> response) {
                                HttpResult<String> httpResult = response.body();

                                if (httpResult.getStatus() == 200) {
                                    if (httpResult.getItems() != null) {
                                        token = httpResult.getItems();
                                        System.out.println("获取到token");
                                        RongIM.getInstance().disconnect();
                                        connect(token);
                                    }
                                }
                            }
                        });

            }
        }
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

        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    System.out.println("onTokenIncorrect");
                    getToken();
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    System.out.println("融云###############################连接成功" + userid);
                    user.setToken(token);
                    DBUtil.insertUser(user);
                    RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
                    RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目

                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    System.out.println("###############################连接失败，" + errorCode);
                }
            });


        }
    }

    @Override
    public void init(Context context) {

        this.context = context;
    }
}
