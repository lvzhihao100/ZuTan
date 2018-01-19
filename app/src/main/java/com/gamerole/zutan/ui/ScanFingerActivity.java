package com.gamerole.zutan.ui;

import android.databinding.ViewDataBinding;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ScanFingerActivityCustom;


/**
 * @author吕志豪 .
 * @date 17-10-25  下午6:10.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_SCAN_FINGER)
public class ScanFingerActivity extends CommonFullTitleActivity {

    //    private RxFingerPrinter rxfingerPrinter;
    private ScanFingerActivityCustom dataBinding;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (ScanFingerActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scan_finger;
    }

    @Override
    public void initData() {
        // where this is an Activity instance
//        rxfingerPrinter = new RxFingerPrinter(this);
    }

    @Override
    public void setView() {

//        // 可以在oncreat方法中执行
//        Subscription subscription =
//                rxfingerPrinter
//                        .begin()
//                        .subscribe(new Subscriber<Boolean>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                if (e instanceof FPerException) {
//                                    Toast.makeText(ScanFingerActivity.this, ((FPerException) e).getDisplayMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onNext(Boolean aBoolean) {
//                                if (aBoolean) {
//                                    Toast.makeText(ScanFingerActivity.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(ScanFingerActivity.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//        rxfingerPrinter.addSubscription(this, subscription); //不要忘记把订阅返回的subscription添加到rxfingerPrinter里
    }

    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        rxfingerPrinter.unSubscribe(this);
    }
}
