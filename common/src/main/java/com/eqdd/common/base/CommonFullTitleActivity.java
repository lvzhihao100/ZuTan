package com.eqdd.common.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import com.eqdd.common.ActivityFullTitleCustom;
import com.eqdd.common.R;


/**
 * Created by lvzhihao on 17-6-9.
 */

public abstract class CommonFullTitleActivity extends CommonActivity {

    private ActivityFullTitleCustom rootBinding;

    @Override
    public void initBinding() {
        rootBinding = DataBindingUtil.setContentView(this, R.layout.common_activity_full_title);
        ViewDataBinding inflate = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        rootBinding.flRoot.addView(inflate.getRoot());
        initBinding(inflate);
    }

    protected abstract void initBinding(ViewDataBinding inflate);

    protected abstract int getLayoutId();

    @Override
    public abstract void initData();

    @Override
    public abstract void setView();

}

