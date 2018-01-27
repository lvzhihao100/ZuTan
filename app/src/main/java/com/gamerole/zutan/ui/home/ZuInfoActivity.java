package com.gamerole.zutan.ui.home;

import android.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.DeviceUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ZuInfoActivityCustom;

/**
 * @author吕志豪 .
 * @date 18-1-26  下午2:55.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ZU_INFO)
public class ZuInfoActivity extends CommonActivity {

    private ZuInfoActivityCustom dataBinding;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_zu_info);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {
        dataBinding.coordinator.setPadding(0, 0, 0, DeviceUtil.getNavigationBarHeight(this));
        DBUtil.getZuLiveData().observe(this, zu -> {
            ImageUtil.setImage(zu.getPoster(), dataBinding.ivPoster);
            dataBinding.toolbar.setTitle(zu.getName());
            dataBinding.tvWatchWord.setText(zu.getWatchword());
            ClickUtil.click(dataBinding.tvMember, () -> ARouter.getInstance().build(RoutConfig.APP_HOME_LIST).navigation());
            ClickUtil.click(dataBinding.tvMap, () -> ARouter.getInstance().build(RoutConfig.APP_SHOW_MAP).navigation());
        });

    }
}
