package com.gamerole.zutan.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lvzhihao on 17-3-25.
 */

public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";


    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            System.out.println("JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            System.out.println("接受到推送下来的自定义消息");
            String ex = bundle.getString(JPushInterface.EXTRA_EXTRA);
            System.out.println(ex);
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            System.out.println(msg);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("接受到推送下来的通知");
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            System.out.println("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        System.out.println(" title : " + title);
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
        System.out.println("alert : " + alert);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        System.out.println("extras : " + extras);
        String type = bundle.getString(JPushInterface.EXTRA_ALERT_TYPE);
        System.out.println("type : " + type);
        String content = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        System.out.println("content : " + content);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        System.out.println(extras);
        String ex = bundle.getString(JPushInterface.EXTRA_EXTRA);
        System.out.println(ex);
        PushBean pushBean = new Gson().fromJson(ex, PushBean.class);
//        if (notiEnters.get(pushBean.getType()) != null) {
//            Intent mIntent = new Intent(context, notiEnters.get(pushBean.getType()));
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        }
    }

    class PushBean {
        int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
