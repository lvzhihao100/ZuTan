package com.gamerole.zutan.fragment;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.eqdd.common.base.BaseFragment;
import com.eqdd.library.RecyclerViewCustom;
import com.gamerole.zutan.R;

/**
 * @author吕志豪 .
 * @date 17-12-23  下午4:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class MsgFragment extends BaseFragment {

    private RecyclerViewCustom dataBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public ViewDataBinding initBinding(ViewDataBinding inflate) {
        return dataBinding = (RecyclerViewCustom) inflate;
    }

    @Override
    public void onClick(View v) {

    }
}
