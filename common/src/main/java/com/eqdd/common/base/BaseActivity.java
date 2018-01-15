package com.eqdd.common.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eqdd.common.R;
import com.eqdd.common.base.loading.INetLoadingView;
import com.eqdd.common.base.loading.waitdialog.DialogHelper;
import com.eqdd.common.base.loading.wheelprogressdialog.WheelProgressHelper;
import com.eqdd.common.utils.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Date;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Created by vzhihao on 2016/11/2.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseView, View.OnClickListener {
    // 可以把常量单独放到一个Class中
    public static final String ACTION_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public INetLoadingView netLoadingView;
    private long oldTime = 0;
    public Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        netLoadingView = new WheelProgressHelper(this);
        initBinding();
        initData();
        setView();
    }


    /**
     * 绑定初始化,加载网络数据时，root要赋值
     */
    public abstract void initBinding();

    /**
     * 初始化数据
     */
    public abstract void initData();


    /**
     * 设置各种view
     */
    public abstract void setView();


    @Override
    public void onBackPressed() {
        Stack<Activity> allActivities = AppManager.getAppManager().getAllActivities();
        if (allActivities.size() <= 1) {
            long nowTime = new Date().getTime();
            if (nowTime - oldTime <= 2000) {
                AppManager.getAppManager().AppExit(this);
            } else {
                oldTime = nowTime;
                ToastUtil.showShort("再次点击退出程序");
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 你可以添加多个Action捕获
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NETWORK_CHANGE);
        registerReceiver(receiver, filter);
        //还可能发送统计数据，比如第三方的SDK 做统计需求
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        //还可能发送统计数据，比如第三方的SDK 做统计需求
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 处理各种情况
            String action = intent.getAction();
            if (ACTION_NETWORK_CHANGE.equals(action)) { // 网络发生变化
                // 处理网络问题


            }
        }
    };

    /**
     * @param visible 返回键是否显示 back
     * @param title   标题 tv_title
     * @param resBg   整体背景色 rl_title
     */
    public void initTopTitleBar(int visible, String title, int resBg) {
        RelativeLayout rl_title = (RelativeLayout) findViewById(R.id.common_rl_title);
        rl_title.setBackgroundColor(resBg);
        ImageView back = (ImageView) findViewById(R.id.common_back);
        back.setVisibility(visible);
        if (visible == View.VISIBLE) {
            back.setOnClickListener(this);
        }
        TextView topbar_title = (TextView) findViewById(R.id.common_tv_title);
        topbar_title.setText(title);
    }

    public void initTopTitleBar(int visible, String title) {
        ImageView back = (ImageView) findViewById(R.id.common_back);
        back.setVisibility(visible);
        if (visible == View.VISIBLE) {
            back.setOnClickListener(this);
        }
        TextView topbar_title = (TextView) findViewById(R.id.common_tv_title);
        topbar_title.setText(title);
    }

    public void initTopTitleBar(String title) {
        initTopTitleBar(View.VISIBLE, title);
    }

    public void initTopRightText(String title, View.OnClickListener onClickListener) {
        TextView textView = (TextView) findViewById(R.id.common_tv_right);
        textView.setText(title);
        textView.setVisibility(View.VISIBLE);
        if (onClickListener != null) {
            RxView.clicks(textView)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe((o) ->
                            onClickListener.onClick(textView));
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.common_back) {
            onBackPressed();
        }
    }

    @Override
    public void showLoading() {
        netLoadingView.showLoading();
    }

    @Override
    public void showLoading(String msg) {
        netLoadingView.showLoading(msg);
    }

    @Override
    public void hideLoading(String msg) {
        netLoadingView.hideLoading(msg);
    }

    @Override
    public void hideLoading() {
        netLoadingView.hideLoading();

    }
}
