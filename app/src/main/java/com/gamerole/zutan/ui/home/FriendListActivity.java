package com.gamerole.zutan.ui.home;

import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.library.RecyclerViewRefreshCustom;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;

/**
 * @author吕志豪 .
 * @date 18-1-6  下午3:56.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_FRIEND_LIST)
public class FriendListActivity extends CommonFullTitleActivity {

    private RecyclerViewRefreshCustom dataBinding;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (RecyclerViewRefreshCustom) inflate;
        initTopTitleBar("好友列表");
        initTopRightText("添加", v -> {
            ARouter.getInstance().build(RoutConfig.APP_ADD_FRIEND).navigation();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview_refresh;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {

    }
}
