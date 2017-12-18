package com.gamerole.zutan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.base.loading.waitdialog.DialogHelper;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.livedata.LocationLiveData;

import retrofit2.http.Path;

/**
 * @author吕志豪 .
 * @date 17-12-15  下午1:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_SHOW_MAP)
public class ShowMapActivity extends BaseActivity {

    private ShowMapActivityCustom dataBinding;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        netLoadingView = new DialogHelper(this);
        initBinding();
        dataBinding.mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        aMap = dataBinding.mapView.getMap();
        initData();
        setView();
    }

    @Override
    public void initBinding() {

        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_map);

    }

    @Override
    public void initData() {
        LocationLiveData.setTime(5 * 1000 * 60);
        LocationLiveData.get().observe(this, System.out::println);
    }

    @Override
    public void setView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBinding.mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBinding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataBinding.mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dataBinding.mapView.onSaveInstanceState(outState);
    }
}
