package com.eqdd.common.http;

import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.utils.ToastUtil;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.Request;

/**
 * Created by lvzhihao on 17-7-4.
 */

public abstract class DialogCallBack<T> extends JsonCallBack<T> {
    BaseActivity baseActivity;

    public DialogCallBack(BaseActivity baseActivity) {
        super();
        this.baseActivity = baseActivity;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        baseActivity.showLoading();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        baseActivity.hideLoading();

    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        ToastUtil.showShort(response.getException().toString());
    }
}
