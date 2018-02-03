package com.gamerole.rongtalk.RouteService;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.Iservice.rongtalk.RongTalkService;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.rongtalk.listener.ConnectionStatusListener;
import com.gamerole.rongtalk.listener.ReceiveMessageListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * Created by lvzhihao on 17-5-31.
 */
@Route(path = RoutConfig.RONGTALK_APPLICATION)
public class RongTalkServiceImpl implements RongTalkService {
    Context context;

    @Override
    public void init(Context context) {
        this.context = context;

    }

    @Override
    public void initRongIm() {

        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(context);
            /**
             * 设置消息体内是否携带用户信息。
             *
             * @param state 是否携带用户信息，true 携带，false 不携带。
             */
            RongIM.getInstance().setMessageAttachedUserInfo(true);
//            RongIMClient.init(contex
        }
        RongIM.setConnectionStatusListener(new ConnectionStatusListener());
        RongIM.setOnReceiveMessageListener(new ReceiveMessageListener());
        Conversation.ConversationType[] types = new Conversation.ConversationType[]{
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP
        };
        RongIM.getInstance().setReadReceiptConversationTypeList(types);
        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
        RongIM.setUserInfoProvider(s -> {
            System.out.println("融云来获取" + s + "的用户数据了");
            OkGo.<HttpResult<RongUserInfo>>get(HttpConfig.BASE_URL + HttpConfig.RONG_GET_USER)
                    .params("userId", s)
                    .execute(new JsonCallBack<HttpResult<RongUserInfo>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<RongUserInfo>> response) {
                            HttpResult<RongUserInfo> httpResult = response.body();
                            if (httpResult.getStatus() == 200) {
                                System.out.println("获取到用户数据了" + httpResult.getItems().getUserId());
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(httpResult.getItems().getUserId(), httpResult.getItems().getName(), Uri.parse(httpResult.getItems().getUri())));
                            }
                        }
                    });
            return null;
        }, true);
        RongIM.setGroupInfoProvider(s -> {
            System.out.println("融云来获取" + s + "的群组数据了");
            OkGo.<HttpResult<RongGroupBean>>get(HttpConfig.BASE_URL + HttpConfig.RONG_GET_GROUP)
                    .params("groupId", s)
                    .execute(new JsonCallBack<HttpResult<RongGroupBean>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<RongGroupBean>> response) {
                            HttpResult<RongGroupBean> httpResult = response.body();
                            if (httpResult.getStatus() == 200) {
                                System.out.println("获取到群组数据了" + httpResult.getItems().getId());
                                RongIM.getInstance().refreshGroupInfoCache(
                                        new Group(httpResult.getItems().getId(), httpResult.getItems().getName(), Uri.parse(httpResult.getItems().getUri())));
                            }
                        }
                    });
            return null;
        }, true);
        RongIM.setGroupUserInfoProvider((s, s1) -> {
            System.out.println("融云来获取" + s + "的群组用户数据了");
            OkGo.<HttpResult<RongGroupUserBean>>get(HttpConfig.BASE_URL + HttpConfig.RONG_GET_GROUP_USER)
                    .params("groupId", s)
                    .params("userId", s1)
                    .execute(new JsonCallBack<HttpResult<RongGroupUserBean>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<RongGroupUserBean>> response) {
                            HttpResult<RongGroupUserBean> httpResult = response.body();
                            if (httpResult.getStatus() == 200) {
                                System.out.println("获取到群组用户数据了" + httpResult.getItems().getUserId());
                                RongIM.getInstance().refreshGroupUserInfoCache(
                                        new GroupUserInfo(httpResult.getItems().getGroupId(), httpResult.getItems().getUserId(),
                                                httpResult.getItems().getNickname()));
                            }
                        }
                    });
            return null;
        }, true);
    }

    public class RongUserInfo {
        String name;
        String userId;
        String uri;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public class RongGroupBean {
        String id;
        String name;
        String uri;

        public RongGroupBean(String id, String name, String uri) {
            this.id = id;
            this.name = name;
            this.uri = uri;
        }

        public String getId() {
            return id;
        }

        public RongGroupBean setId(String id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public RongGroupBean setName(String name) {
            this.name = name;
            return this;
        }

        public String getUri() {
            return uri;
        }

        public RongGroupBean setUri(String uri) {
            this.uri = uri;
            return this;
        }
    }

    public class RongGroupUserBean {
        String userId;
        String groupId;
        String nickname;

        public RongGroupUserBean(String userId, String groupId, String nickname) {
            this.userId = userId;
            this.groupId = groupId;
            this.nickname = nickname;
        }

        public String getUserId() {
            return userId;
        }

        public RongGroupUserBean setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getGroupId() {
            return groupId;
        }

        public RongGroupUserBean setGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public String getNickname() {
            return nickname;
        }

        public RongGroupUserBean setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
