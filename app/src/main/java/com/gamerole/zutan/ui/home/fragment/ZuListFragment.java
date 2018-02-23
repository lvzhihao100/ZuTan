package com.gamerole.zutan.ui.home.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.BaseFragment;
import com.eqdd.common.box.ItemDecorate.DividerLineItemDecoration;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.mvchelper.ModelRx2DataSource;
import com.eqdd.common.mvchelper.Rx2DataSource;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.RecyclerViewRefreshCustom;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpPageResult;
import com.gamerole.zutan.R;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.shizhefei.mvc.MVCCoolHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-2-8  上午11:54.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_FRAGMENT_ZU_LIST)
public class ZuListFragment extends BaseFragment {
    RecyclerViewRefreshCustom dataBinding;
    private int pageNum;
    private SlimAdapterEx<Zu> slimAdapterEx;

    private String query;
    private MVCCoolHelper<List<Zu>> mvcHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview_refresh;
    }


    @Override
    protected void setView() {

    }

    @Override
    protected void initData() {
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity()));
        slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_15, new SlimInjector<Zu>() {
            @Override
            public void onInject(Zu data, IViewInjector injector) {
                injector.text(R.id.tv_content, data.getName())
                        .imageCircle(R.id.iv_head, data.getLogo());
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
        ItemClickSupport.addTo(dataBinding.recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> ARouter.getInstance().build(RoutConfig.APP_ZU_SHOW).withLong(Config.ID, slimAdapterEx.getDataItem(position).getId()).navigation());
        mvcHelper = new MVCCoolHelper<>(dataBinding.coolRefreshView);
        ModelRx2DataSource<Zu> dataSource = new ModelRx2DataSource<>(new ModelRx2DataSource.OnLoadSource() {
            @Override
            public Flowable<List> loadSource(int page, Rx2DataSource.DoneActionRegister register) {
                pageNum = page - 1;
                return OkGo.<HttpPageResult<Zu>>get(HttpConfig.BASE_URL + HttpConfig.ZU_QUERY)
                        .params("page", pageNum)
                        .params("query", query)
                        .converter(new JsonConverter<HttpPageResult<Zu>>() {
                            @Override
                            public void test() {
                                super.test();
                            }
                        })
                        .adapt(new FlowableBody<>())
                        .flatMap(listHttpResult -> {
                            if (listHttpResult.getStatus() == 200) {
                                return Flowable.just(listHttpResult.getItems() == null ? new ArrayList<Zu>() : listHttpResult.getItems().getContent());
                            } else {
                                ToastUtil.showShort(listHttpResult.getMsg());
                                return Flowable.just(new ArrayList<>());
                            }
                        });
            }
        }, 10);

        mvcHelper.setDataSource(dataSource);
        mvcHelper.setAdapter(slimAdapterEx);
    }

    @Override
    public ViewDataBinding initBinding(ViewDataBinding inflate) {
        return dataBinding = (RecyclerViewRefreshCustom) inflate;

    }

    @Override
    public void onClick(View v) {

    }

    public void refresh(String currentQuery) {
        this.query = currentQuery;
        mvcHelper.refresh();
    }
}
