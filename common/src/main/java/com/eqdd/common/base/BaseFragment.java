package com.eqdd.common.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by vzhihao on 2016/11/2.
 */
public abstract class BaseFragment extends RxFragment implements View.OnClickListener {
    private ViewDataBinding fragmentCustom;


    @Override
    public void onAttach(Context context) {
        System.out.println(getClass().getSimpleName() + "onAttach" + "on");
        super.onAttach(context);
        System.out.println(getClass().getSimpleName() + "onAttach" + "off");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println(getClass().getSimpleName() + "onCreate" + "on");

        super.onCreate(savedInstanceState);
        System.out.println(getClass().getSimpleName() + "onCreate" + "off");

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println(getClass().getSimpleName() + "onCreateView" + "on");

        if (fragmentCustom == null) {
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

    @Override
    public void onStart() {
        System.out.println(getClass().getSimpleName() + "onStart" + "on");

        super.onStart();
        System.out.println(getClass().getSimpleName() + "onStart" + "off");

    }

    @Override
    public void onResume() {
        System.out.println(getClass().getSimpleName() + "onResume" + "on");

        super.onResume();
        System.out.println(getClass().getSimpleName() + "onResume" + "off");

    }

    @Override
    public void onPause() {
        System.out.println(getClass().getSimpleName() + "onPause" + "on");

        super.onPause();
        System.out.println(getClass().getSimpleName() + "onPause" + "off");

    }

    @Override
    public void onStop() {
        System.out.println(getClass().getSimpleName() + "onStop" + "on");
        super.onStop();
        System.out.println(getClass().getSimpleName() + "onStop" + "off");
    }

    @Override
    public void onDestroyView() {
        System.out.println(getClass().getSimpleName() + "onDestroyView" + "on");

        super.onDestroyView();
        System.out.println(getClass().getSimpleName() + "onDestroyView" + "off");
    }

    @Override
    public void onDestroy() {
        System.out.println(getClass().getSimpleName() + "onDestroy" + "on");
        super.onDestroy();
        System.out.println(getClass().getSimpleName() + "onDestroy" + "off");
    }

    @Override
    public void onDetach() {
        System.out.println(getClass().getSimpleName() + "onDetach" + "on");
        super.onDetach();
        System.out.println(getClass().getSimpleName() + "onDetach" + "off");
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
