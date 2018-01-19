package com.gamerole.rongtalk.RouteService;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.library.Iservice.rongtalk.RongTalkService;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.rongtalk.listener.ConnectionStatusListener;
import com.gamerole.rongtalk.listener.ReceiveMessageListener;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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
//            RongIMClient.init(context);
        }
        RongIM.setConnectionStatusListener(new ConnectionStatusListener());
        RongIM.setOnReceiveMessageListener(new ReceiveMessageListener());
        Conversation.ConversationType[] types = new Conversation.ConversationType[]{
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP
        };
        RongIM.getInstance().setReadReceiptConversationTypeList(types);
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
