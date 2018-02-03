package com.gamerole.zutan.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.autonavi.amap.mapcore.Inner_3dMap_location;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimAdapterExReverse;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.BaseActivity;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.library.utils.HttpUtil;
import com.gamerole.zutan.AppListItem21Custom;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ShowMapActivityCustom;
import com.gamerole.zutan.bean.UserLocationBean;
import com.gamerole.zutan.box.PagerMapSnapHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private SlimAdapterEx<User> slimAdapterEx;
    private ArrayList<User> userLocationBeans = new ArrayList<>();
    private SparseArray<Marker> makers = new SparseArray();
    private Map<Long, Integer> userPos = new HashMap<>();
    @Autowired
    long id;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_map);
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
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
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        dataBinding.recyclerView.scrollToPosition(5000);
        PagerMapSnapHelper linearSnapHelper = new PagerMapSnapHelper();
        linearSnapHelper.attachToRecyclerView(dataBinding.recyclerView);
        linearSnapHelper.setOnPosChange(curPos -> {
            User dataItem = slimAdapterEx.getDataItem(curPos % slimAdapterEx.getData().size());
            //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
            Marker marker = makers.get(userPos.get(dataItem.getId()));
            if (marker != null) {
//                marker.setVisible(true);
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLng(marker.getPosition());
                aMap.animateCamera(mCameraUpdate);
            } else {
                ToastUtil.showShort("位置信息未上传");
            }
        });
        ClickUtil.click(dataBinding.btRefresh, this::updateLocationView);
        getAllUser();
    }

    private void updateLocationView() {
        OkGo.<HttpResult<List<UserLocationBean>>>get(HttpConfig.BASE_URL + HttpConfig.GET_USERS_LOCATION)
                .params("zuId", id)
                .converter(new JsonConverter<HttpResult<List<UserLocationBean>>>() {
                    @Override
                    public void test() {
                        super.test();
                    }
                }).adapt(new FlowableBody<>())
                .flatMap(listHttpResult -> Flowable.fromIterable(listHttpResult.getItems()))
                .filter(userLocationBean -> userLocationBean.getLocationId() > 0)
                .subscribe(this::refreshLocation, System.out::print, () -> ToastUtil.showShort("已刷新"));
    }

    private void getAllUser() {
        userLocationBeans.clear();
        OkGo.<HttpResult<List<User>>>get(HttpConfig.BASE_URL + HttpConfig.ZU_USER_PAGE_LIST)
                .params("page", -1)
                .params("zuId", id)
                .converter(new JsonConverter<HttpResult<List<User>>>() {
                    @Override
                    public void test() {
                        super.test();
                    }
                }).adapt(new FlowableBody<>())
                .flatMap(listHttpResult -> Flowable.fromIterable(listHttpResult.getItems()))
                .subscribe(this::updateView, System.out::print, () -> {
                    if (slimAdapterEx == null) {
                        slimAdapterEx = SlimAdapterExReverse.create().register(R.layout.app_list_item_20, new SlimInjector<User>() {
                            @Override
                            public void onInject(User data, IViewInjector injector) {
                                injector.imageCircle(R.id.iv_head, data.getPhoto())
                                        .text(R.id.tv_name, data.getName());
                            }
                        }).attachTo(dataBinding.recyclerView).updateData(userLocationBeans);
                        ItemClickSupport.addTo(dataBinding.recyclerView)
                                .setOnItemClickListener((recyclerView, position, v) -> {
                                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ShowMapActivity.this
                                            , new Pair(v.findViewById(R.id.iv_head), "shared_image_")
                                            , new Pair(v.findViewById(R.id.tv_name), "shared_text_"));
                                    ARouter.getInstance()
                                            .build(RoutConfig.APP_USER_INFO)
                                            .withObject(Config.USER, slimAdapterEx.getDataItem(position % slimAdapterEx.getData().size()))
                                            .withOptionsCompat(activityOptionsCompat)
                                            .navigation(ShowMapActivity.this);
                                });
                    } else {
                        slimAdapterEx.updateData(userLocationBeans);
                    }
                });
    }

    private void updateView(User item) {
        userLocationBeans.add(item);
        userPos.put(item.getId(), userLocationBeans.indexOf(item));
    }

    private void refreshLocation(UserLocationBean item) {
        if (item.getLocationId() > 0 && userPos.get(item.getUserId()) > -1) {
            Marker smoothMoveMarker = makers.get(userPos.get(item.getUserId()));
            if (smoothMoveMarker == null) {
                AppListItem21Custom inflate = DataBindingUtil.inflate(getLayoutInflater(), R.layout.app_list_item_21, null, false);
                ImageUtil.setCircleImageReady(item.getCatongImg(), drawable -> {

                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.title(item.getAddress()).snippet(item.getUpdateTime());
                    inflate.ivHead.setImageDrawable(drawable);
                    inflate.tvName.setText(item.getName());
                    markerOption.icon(BitmapDescriptorFactory.fromView(inflate.getRoot()));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(true);//设置marker平贴地图效果
                    markerOption.position(new LatLng(item.getLatitude(), item.getLongitude()));
//                    markerOption.visible(false);
                    Marker marker = aMap.addMarker(markerOption);
                    makers.put(userPos.get(item.getUserId()), marker);
                });
            } else {
                smoothMoveMarker.setPosition(new LatLng(item.getLatitude(), item.getLongitude()));
            }
        }
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
