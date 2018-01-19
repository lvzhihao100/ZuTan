package com.eqdd.common.base;

import android.support.annotation.StringRes;

/**
 * Created by vzhihao on 2016/11/17.
 */
public interface BaseView {
     void showLoading();//显示加载框
     void showLoading(String msg);//设置加载时数据

    void showLoading(@StringRes int resId);

    void hideLoading(String msg);//关闭加载匡

    void hideLoading(@StringRes int resId);

    void hideLoading();//关闭加载匡

}
