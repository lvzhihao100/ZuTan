package com.eqdd.common.base.loading.wheelprogressdialog;

import android.content.Context;


import com.eqdd.common.base.loading.INetLoadingView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by vzhihao on 2017/3/12.
 */
public class WheelProgressHelper implements INetLoadingView {

    private WheelProgressDialog wheelProgressDialog;
    private int wheelProgress;
    private String endMsg = "加载结束";
    private String loadingMsg = "加载中";
    Disposable subscribe;

    public WheelProgressHelper(Context context) {
        if (wheelProgressDialog == null) {
            try {
                wheelProgressDialog = new WheelProgressDialog(context);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("WheelProgressHelper" + e.toString());
            }
        }
    }

    private void showBinner() {

        subscribe = Flowable.just(1)
                .subscribeOn(Schedulers.io())
                .interval(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (++wheelProgress <= 110) {
                        if (wheelProgress > 90 && wheelProgress < 100) {
                            wheelProgress = 90;
                        }
                        wheelProgressDialog.progress(wheelProgress >= 100 ? 100 : wheelProgress).message(wheelProgress >= 100 ? endMsg : loadingMsg);
                    } else {
                        subscribe.dispose();
                        if (wheelProgressDialog.isShowing()) {
                            wheelProgressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void showLoading() {
        wheelProgress = 0;
        if (subscribe==null||subscribe.isDisposed()) {
            showBinner();
        }
        wheelProgressDialog.show();
    }

    @Override
    public void showLoading(String msg) {
        loadingMsg = msg;
        showLoading();
    }

    @Override
    public void showLoading(String msg, boolean isContinue) {
        if (!isContinue) {
            wheelProgress = 0;
        }
        loadingMsg = msg;
        if (subscribe==null||subscribe.isDisposed()) {
            showBinner();
        }
        wheelProgressDialog.show();
    }

    @Override
    public void hideLoading(String msg) {
        if (subscribe==null||subscribe.isDisposed()) {
            showBinner();
        }
        wheelProgress = 100;
        endMsg = msg;
    }

    @Override
    public void dismiss() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            subscribe = null;
        }
        wheelProgressDialog.dismiss();
    }

    @Override
    public void hideLoading() {
        wheelProgress = 100;
        if (subscribe==null||subscribe.isDisposed()) {
            showBinner();
        }
    }
}
