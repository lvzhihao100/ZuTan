package com.eqdd.common.base.loading.waitdialog;


import android.content.Context;

import com.eqdd.common.R;
import com.eqdd.common.base.loading.INetLoadingView;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class DialogHelper implements INetLoadingView {


    private WaitDialog dialog;

    public DialogHelper(Context context) {
        dialog = null;
        try {
            dialog = new WaitDialog(context, R.style.common_CDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showLoading() {

        dialog.show();
    }

    @Override
    public void showLoading(final String msg) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            dialog.setMessage(msg);
            dialog.show();
        });


    }

    @Override
    public void showLoading(String msg, boolean isContinue) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            dialog.setMessage(msg);
            dialog.show();
        });
    }

    @Override
    public void hideLoading(String msg) {
        dialog.hide();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void hideLoading() {
        dialog.hide();
    }

}
