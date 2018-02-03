package com.gamerole.zutan.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.MyFragmentPagerAdapter;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.library.Iservice.rongtalk.RongRefreshService;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.MainActivityCustom;
import com.gamerole.zutan.R;
import com.gamerole.zutan.fragment.HomeFragment;
import com.gamerole.zutan.fragment.MineFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

@Route(path = RoutConfig.APP_HOME)
public class MainActivity extends CommonActivity {

    private MainActivityCustom dataBinding;
    @Autowired
    RongRefreshService rongRefreshService;


    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_main);
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        DBUtil.getUser().observe(this, user -> {
            rongRefreshService.setCurrentUser(user);
            rongRefreshService.refreshUserCache(user);
        });
        DBUtil.getUserStatic(user -> OkGo.<HttpResult<Zu>>get(HttpConfig.BASE_URL + HttpConfig.APP_ZU_QUERY)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(HttpConfig.BASE_URL + HttpConfig.APP_ZU_QUERY + user.getZuId())
                .params("id", user.getZuId())
                .execute(new JsonCallBack<HttpResult<Zu>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<Zu>> response) {
                        HttpResult<Zu> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            Zu zu = httpResult.getItems();
                            DBUtil.insertZu(zu);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<HttpResult<Zu>> response) {
                        super.onCacheSuccess(response);
                        HttpResult<Zu> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            Zu zu = httpResult.getItems();
                            DBUtil.insertZu(zu);
                        }
                    }
                }));

    }

    @Override
    public void setView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Fragment fragment = (Fragment) ARouter.getInstance().build(RoutConfig.RONGTALK_FRAGMENT_MSG).navigation();
        fragments.add(fragment);
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());

        dataBinding.viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));

        dataBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dataBinding.bottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dataBinding.bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_msg:
                    dataBinding.viewPager.setCurrentItem(0);
                    break;
                case R.id.tab_home:
                    dataBinding.viewPager.setCurrentItem(1);
                    break;
                case R.id.tab_info:
                    dataBinding.viewPager.setCurrentItem(2);
                    break;
            }
        });
    }
}
