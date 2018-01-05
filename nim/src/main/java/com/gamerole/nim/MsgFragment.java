package com.gamerole.nim;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.eqdd.common.base.BaseFragment;
import com.eqdd.library.LibraryRecyclerViewCustom;

/**
 * @author吕志豪 .
 * @date 17-12-23  下午4:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class MsgFragment extends BaseFragment {

    private LibraryRecyclerViewCustom dataBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.nim_recent_contacts_fragment;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public ViewDataBinding initBinding(ViewDataBinding inflate) {
        return dataBinding = (LibraryRecyclerViewCustom) inflate;
    }

    @Override
    public void onClick(View v) {

    }
}
