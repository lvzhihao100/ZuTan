package com.eqdd.common.base.loading;

/**
 * Created by vzhihao on 2017/3/12.
 */
public interface INetLoadingView {
    void showLoading();//显示加载框
    void showLoading(String msg);//设置加载时数据

    void showLoading(String msg, boolean isContinue);

    void hideLoading(String msg);//关闭加载匡

    void dismiss();

    void hideLoading();//关闭加载匡
}
