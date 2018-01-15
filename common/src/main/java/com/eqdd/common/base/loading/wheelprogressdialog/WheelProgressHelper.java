package com.eqdd.common.base.loading.wheelprogressdialog;

import android.content.Context;


import com.eqdd.common.base.loading.INetLoadingView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by vzhihao on 2017/3/12.
 */
public class WheelProgressHelper implements INetLoadingView {

    private WheelProgressDialog wheelProgressDialog;
    private int wheelProgress;
    private Subscription subscribe;
    private String endMsg = "加载结束";
    private String loadingMsg = "加载中";

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
        subscribe = Observable.just(1)
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
                        subscribe.unsubscribe();
                        if (wheelProgressDialog.isShowing()) {
                            wheelProgressDialog.hide();
                        }
                    }
                });
    }

    @Override
    public void showLoading() {
        wheelProgress = 0;
        if (subscribe == null) {
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
    public void hideLoading(String msg) {
        wheelProgress = 100;
        endMsg = msg;
    }

    @Override
    public void hideLoading() {
        wheelProgress = 100;
    }
}
