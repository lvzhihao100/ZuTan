package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.common.utils.RefreshUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.Iservice.rongtalk.RongStartService;
import com.eqdd.library.RecyclerViewRefreshCustom;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RequestConfig;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author吕志豪 .
 * @date 18-2-3  上午11:49.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ZU_LIST)
public class ZuListActivity extends CommonFullTitleActivity {

    private RecyclerViewRefreshCustom dataBinding;
    private SlimAdapterEx<Zu> slimAdapterEx;
    @Autowired
    RongStartService rongStartService;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (RecyclerViewRefreshCustom) inflate;
        initTopTitleBar("族列表");
        initTopRightText("创建", view -> ARouter.getInstance().build(RoutConfig.APP_ZU_GUIDE).navigation(this, RequestConfig.APP_ZU_GUIDE));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview_refresh;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);

        RefreshUtil.openRefresh(dataBinding.coolRefreshView, coolRefreshView -> {
            refresh();
        });
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_15, new SlimInjector<Zu>() {
            @Override
            public void onInject(Zu data, IViewInjector injector) {
                injector.text(R.id.tv_content, data.getName())
                        .imageCircle(R.id.iv_head, data.getLogo());
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
        ItemClickSupport.addTo(dataBinding.recyclerView)
                .setOnItemClickListener((recyclerView, position, v) ->
                        rongStartService.startGroup(this, slimAdapterEx.getDataItem(position).getId() + "", slimAdapterEx.getDataItem(position).getName()));
        dataBinding.coolRefreshView.setRefreshing(true);
    }

    private void refresh() {
        OkGo.<HttpResult<List<Zu>>>get(HttpConfig.BASE_URL + HttpConfig.APP_ZU_LIST)
                .execute(new JsonCallBack<HttpResult<List<Zu>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<Zu>>> response) {
                        HttpResult<List<Zu>> httpResult = response.body();
                        dataBinding.coolRefreshView.setRefreshing(false);
                        if (httpResult.getStatus() == 200) {
                            slimAdapterEx.updateData(httpResult.getItems());
                        }
                    }

                    @Override
                    public void onError(Response<HttpResult<List<Zu>>> response) {
                        super.onError(response);
                        dataBinding.coolRefreshView.setRefreshing(false);
                    }
                });
    }

    @Override
    public void setView() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestConfig.APP_ZU_GUIDE && resultCode == Config.SUCCESS) {
            dataBinding.coolRefreshView.setRefreshing(true);
        }
    }
}
