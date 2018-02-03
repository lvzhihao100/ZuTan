package com.gamerole.zutan.ui.home;

import android.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.DeviceUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ZuInfoActivityCustom;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;

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
    @Autowired
    long id;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_zu_info);
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);

    }

    @Override
    public void setView() {
        OkGo.<HttpResult<Zu>>get(HttpConfig.BASE_URL + HttpConfig.APP_ZU_QUERY)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .params("id", id)
                .execute(new DialogCallBack<HttpResult<Zu>>(this) {
                    @Override
                    public void onSuccess(Response<HttpResult<Zu>> response) {
                        HttpResult<Zu> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            Zu zu = httpResult.getItems();
                            ImageUtil.setImage(zu.getPoster(), dataBinding.ivPoster);
                            dataBinding.toolbar.setTitle(zu.getName());
                            dataBinding.tvWatchWord.setText(zu.getWatchword());
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<HttpResult<Zu>> response) {
                        super.onCacheSuccess(response);
                        HttpResult<Zu> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            Zu zu = httpResult.getItems();
                            ImageUtil.setImage(zu.getPoster(), dataBinding.ivPoster);
                            dataBinding.toolbar.setTitle(zu.getName());
                            dataBinding.tvWatchWord.setText(zu.getWatchword());
                        }
                    }
                });
        dataBinding.coordinator.setPadding(0, 0, 0, DeviceUtil.getNavigationBarHeight(this));
        ClickUtil.click(dataBinding.tvMember, () -> ARouter.getInstance().build(RoutConfig.APP_ZU_USER_LIST).withLong(Config.ID,id).navigation());
        ClickUtil.click(dataBinding.tvMap, () -> ARouter.getInstance().build(RoutConfig.APP_SHOW_MAP).withLong(Config.ID,id).navigation());
        ClickUtil.click(dataBinding.ivBack, this::onBackPressed);

    }
}
