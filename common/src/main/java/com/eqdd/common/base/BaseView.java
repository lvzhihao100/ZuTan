package com.eqdd.common.base;

/**
 * Created by vzhihao on 2016/11/17.
 */
public interface BaseView {
     void showLoading();//显示加载框
     void showLoading(String msg);//设置加载时数据
     void hideLoading(String msg);//关闭加载匡
     void hideLoading();//关闭加载匡

}
