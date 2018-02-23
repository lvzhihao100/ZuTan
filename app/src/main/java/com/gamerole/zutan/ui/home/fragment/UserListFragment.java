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
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.mvchelper.ModelRx2DataSource;
import com.eqdd.common.mvchelper.Rx2DataSource;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.LibraryOnlyRecyclerViewCustom;
import com.eqdd.library.RecyclerViewRefreshCustom;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpPageResult;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ui.SearchActivity;
import com.gamerole.zutan.ui.home.ZuUserListActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.shizhefei.mvc.MVCCoolHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-2-7  上午11:06.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_FRAGMENT_USER_LIST)
public class UserListFragment extends BaseFragment {
    RecyclerViewRefreshCustom dataBinding;
    private int pageNum;
    private SlimAdapterEx<User> slimAdapterEx;

    private String query;
    @Autowired
    boolean isSelect = false;
    private MVCCoolHelper<List<User>> mvcHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview_refresh;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.library_list_divider));
        dataBinding.recyclerView.addItemDecoration(divider);
        slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_63, new SlimInjector<User>() {
            @Override
            public void onInject(User data, IViewInjector injector) {

                injector.imageCircle(R.id.iv_poster, data.getPhoto())
                        .text(R.id.tv_name, data.getName())
                        .text(R.id.tv_address, data.getAddress());
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
        ItemClickSupport.addTo(dataBinding.recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    if (isSelect) {
                        Intent intent = new Intent();
                        intent.putExtra(Config.BEAN_SERIALIZABLE, slimAdapterEx.getDataItem(position));
                        getActivity().setResult(Config.SUCCESS, intent);
                        getActivity().finish();
                    } else {
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()
                                , new Pair(v.findViewById(R.id.iv_poster), "shared_image_")
                                , new Pair(v.findViewById(R.id.tv_name), "shared_text_"));
                        ARouter.getInstance()
                                .build(RoutConfig.APP_USER_INFO)
                                .withObject("user", slimAdapterEx.getDataItem(position))
                                .withOptionsCompat(activityOptionsCompat)
                                .navigation(getActivity());
                    }
                });
        mvcHelper = new MVCCoolHelper<>(dataBinding.coolRefreshView);
        ModelRx2DataSource<User> dataSource = new ModelRx2DataSource<>(new ModelRx2DataSource.OnLoadSource() {
            @Override
            public Flowable<List> loadSource(int page, Rx2DataSource.DoneActionRegister register) {
                pageNum = page - 1;
                return OkGo.<HttpPageResult<User>>get(HttpConfig.BASE_URL + HttpConfig.USER_QUERY)
                        .params("page", pageNum)
                        .params("query", query)
                        .converter(new JsonConverter<HttpPageResult<User>>() {
                            @Override
                            public void test() {
                                super.test();
                            }
                        })
                        .adapt(new FlowableBody<>())
                        .flatMap(listHttpResult -> {
                            if (listHttpResult.getStatus() == 200) {
                                return Flowable.just(listHttpResult.getItems() == null ? new ArrayList<User>() : listHttpResult.getItems().getContent());
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
