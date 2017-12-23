package com.eqdd.common.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;


/**
 * Created by vzhihao on 2016/11/2.
 */
public abstract class BaseFragment extends RxFragment implements View.OnClickListener {
    private ViewDataBinding fragmentCustom;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            fragmentCustom = initBinding(DataBindingUtil.inflate(inflater, getLayoutId(), null, false));
        }
        ViewGroup parent = (ViewGroup) fragmentCustom.getRoot().getParent();
        if (parent != null) {
            parent.removeView(fragmentCustom.getRoot());
        }
        initData();
        setView();
        return fragmentCustom.getRoot();
    }

    protected abstract int getLayoutId();

    protected abstract void setView();

    protected abstract void initData();

    public abstract ViewDataBinding initBinding(ViewDataBinding inflate);

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


}
