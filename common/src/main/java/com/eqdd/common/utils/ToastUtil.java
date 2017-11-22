package com.eqdd.common.utils;

import android.widget.Toast;

import com.eqdd.common.base.App;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by vzhihao on 2016/8/24.
 */
public class ToastUtil {

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        AndroidSchedulers.mainThread().createWorker().schedule(() ->
                Toast.makeText(App.INSTANCE, message, Toast.LENGTH_LONG).show()
        );
    }

    public static void showShort(CharSequence message) {
        AndroidSchedulers.mainThread().createWorker().schedule(() ->
                Toast.makeText(App.INSTANCE, message, Toast.LENGTH_SHORT).show()
        );
    }
}
