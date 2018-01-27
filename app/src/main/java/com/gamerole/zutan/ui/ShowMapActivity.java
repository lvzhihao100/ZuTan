package com.gamerole.zutan.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.autonavi.amap.mapcore.Inner_3dMap_location;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimAdapterExReverse;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.App;
import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.library.utils.HttpUtil;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ShowMapActivityCustom;
import com.gamerole.zutan.ZuTanApplication;
import com.gamerole.zutan.bean.UserLocationBean;
import com.gamerole.zutan.box.PagerMapSnapHelper;
import com.gamerole.zutan.livedata.LocationLiveData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.FlowableBody;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

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
    private boolean isFirst = true;
    private SlimAdapterEx<UserLocationBean> slimAdapterEx;
    private ArrayList<UserLocationBean> userLocationBeans = new ArrayList<>();

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_map);
    }

    @Override
    public void initData() {
        dataBinding.mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        aMap = dataBinding.mapView.getMap();
    }

    @Override
    public void setView() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(20000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setCompassEnabled(true);//指南针用于向 App 端用户展示地图方向，默认不显示。
        aMap.getUiSettings().setScaleControlsEnabled(true);//比例尺控件（最大比例是1：10m,最小比例是1：1000Km），位于地图右下角，可控制其显示与隐藏
        aMap.getUiSettings().setLogoBottomMargin(-50);//设置隐藏高徳Logo
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(location -> {
            System.out.println(location);
            if (isFirst) {
                //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 16, 30, 0));
                aMap.moveCamera(mCameraUpdate);
                isFirst = false;
            }
            Inner_3dMap_location inLocation = ((Inner_3dMap_location) location);
            if (inLocation.getErrorCode() == 0) {
                HttpUtil.updateLocation(location, inLocation.getProvince(), inLocation.getCity(), inLocation.getDistrict(), inLocation.getAddress());
            }
        });
        DBUtil.getUserStatic(user -> getAllLocation(user.getZuId()));
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        dataBinding.recyclerView.scrollToPosition(5000);
        PagerMapSnapHelper linearSnapHelper = new PagerMapSnapHelper();
        linearSnapHelper.attachToRecyclerView(dataBinding.recyclerView);
        linearSnapHelper.setOnPosChange(curPos -> {
            UserLocationBean dataItem = slimAdapterEx.getDataItem(curPos % slimAdapterEx.getData().size());
            //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
            CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(dataItem.getLatitude(), dataItem.getLongitude()), 16, 30, 0));
            aMap.animateCamera(mCameraUpdate);
        });
    }

    private void getAllLocation(long zuId) {
        userLocationBeans.clear();
        OkGo.<HttpResult<List<UserLocationBean>>>get(HttpConfig.BASE_URL + HttpConfig.GET_USERS_LOCATION)
                .params("zuId", zuId)
                .converter(new JsonConverter<HttpResult<List<UserLocationBean>>>() {
                    @Override
                    public void test() {
                        super.test();
                    }
                }).adapt(new FlowableBody<>())
                .flatMap(listHttpResult -> Flowable.fromIterable(listHttpResult.getItems()))
                .filter(userLocationBean -> userLocationBean.getLocationId() > 0)
                .subscribe(this::refreshLocation, System.out::print, () -> {
                    if (slimAdapterEx == null) {
                        slimAdapterEx = SlimAdapterExReverse.create().register(R.layout.app_list_item_20, new SlimInjector<UserLocationBean>() {
                            @Override
                            public void onInject(UserLocationBean data, IViewInjector injector) {
                                injector.image(R.id.iv_head, data.getCatongImg())
                                        .text(R.id.tv_name, data.getName());
                            }
                        }).attachTo(dataBinding.recyclerView).updateData(userLocationBeans);
                    } else {
                        slimAdapterEx.updateData(userLocationBeans);
                    }

                });
    }

    private void refreshLocation(UserLocationBean item) {
        userLocationBeans.add(item);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(item.getLatitude(), item.getLongitude()));
        markerOption.title(item.getAddress()).snippet(item.getName());
        ImageUtil.bitmap(item.getCatongImg(), bitmap -> {
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            aMap.addMarker(markerOption);
        });
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
