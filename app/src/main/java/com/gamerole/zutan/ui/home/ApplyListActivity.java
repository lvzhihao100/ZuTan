package com.gamerole.zutan.ui.home;

import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.RecyclerViewRefreshCustom;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;

/**
 * @author吕志豪 .
 * @date 18-1-20  下午4:02.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ZU_APPLY_LIST)
public class ApplyListActivity extends CommonFullTitleActivity {

    private RecyclerViewRefreshCustom dataBinding;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (RecyclerViewRefreshCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview_refresh;
    }

    @Override
    public void initData() {

        SlimAdapterEx.create().register(R.layout.library_list_item_19, new SlimInjector() {
            @Override
            public void onInject(Object data, IViewInjector injector) {

            }
        });
    }

    @Override
    public void setView() {

    }
}
