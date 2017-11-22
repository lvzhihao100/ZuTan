package com.gamerole.zutan;

import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.base.RoutConfig;

/**
 * @author吕志豪 .
 * @date 17-11-15  下午4:49.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_TEST)
public class TestActivity extends CommonFullTitleActivity {

    private TestActivityCustom dataBinding;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (TestActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_test;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {

        dataBinding.tv.setOnClickListener(v -> {
            ViewCompat.offsetTopAndBottom(v, 5);
        });
    }
}
