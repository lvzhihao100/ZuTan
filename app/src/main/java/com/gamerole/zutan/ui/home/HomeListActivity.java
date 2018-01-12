package com.gamerole.zutan.ui.home;

import android.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;

/**
 * @author吕志豪 .
 * @date 18-1-10  下午3:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_HOME_LIST)
public class HomeListActivity extends CommonActivity {
    @Override
    public void initBinding() {
        DataBindingUtil.setContentView(this, R.layout.app_activity_home_list);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {

    }
}
