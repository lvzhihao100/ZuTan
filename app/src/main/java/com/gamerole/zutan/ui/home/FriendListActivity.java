package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.mvchelper.ModelRx2DataSource;
import com.eqdd.common.mvchelper.Rx2DataSource;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RequestConfig;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.Friend;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpPageResult;
import com.gamerole.zutan.FriendListActivityCustom;
import com.gamerole.zutan.R;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.shizhefei.mvc.MVCCoolHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-1-6  下午3:56.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_FRIEND_LIST)
public class FriendListActivity extends CommonActivity {

    private SlimAdapterEx slimAdapterEx;
    private FriendListActivityCustom dataBinding;
    private ModelRx2DataSource<Friend> dataSource;
    private MVCCoolHelper<List<Friend>> mvcHelper;
    private int pageNum;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_friend_list);
        ClickUtil.click(dataBinding.tvAdd, () -> {
            ARouter.getInstance().build(RoutConfig.APP_ADD_FRIEND).navigation(FriendListActivity.this, RequestConfig.APP_ADD_FRIEND);
        });
    }

    @Override
    public void initData() {
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.library_list_divider));
        dataBinding.recyclerView.addItemDecoration(divider);

        slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_63, new SlimInjector<Friend>() {
            @Override
            public void onInject(Friend data, IViewInjector injector) {

                injector.imageCircle(R.id.iv_poster, data.getPoster())
                        .text(R.id.tv_name, data.getName())
                        .text(R.id.tv_address, data.getAddress());
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
        ItemClickSupport.addTo(dataBinding.recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(FriendListActivity.this
                            , new Pair(v.findViewById(R.id.iv_poster), "shared_image_")
                            , new Pair(v.findViewById(R.id.tv_name), "shared_text_"));
                    ARouter.getInstance()
                            .build(RoutConfig.APP_FRIEND_INFO)
                            .withObject("friend", slimAdapterEx.getDataItem(position))
                            .withOptionsCompat(activityOptionsCompat)
                            .navigation(FriendListActivity.this);

                });
        mvcHelper = new MVCCoolHelper<>(dataBinding.coolRefreshView);
        dataSource = new ModelRx2DataSource<>(new ModelRx2DataSource.OnLoadSource() {
            @Override
            public Flowable<List> loadSource(int page, Rx2DataSource.DoneActionRegister register) {
                pageNum = page - 1;
                return OkGo.<HttpPageResult<Friend>>get(HttpConfig.BASE_URL + HttpConfig.FRIEND_PAGE_LIST)
                        .params("page", pageNum)
                        .converter(new JsonConverter<HttpPageResult<Friend>>() {
                            @Override
                            public void test() {
                                super.test();
                            }
                        })
                        .adapt(new FlowableBody<>())
                        .flatMap(listHttpResult -> {
                            if (listHttpResult.getStatus() == 200) {
                                return Flowable.just(listHttpResult.getItems() == null ? new ArrayList<Friend>() : listHttpResult.getItems().getContent());
                            } else {
                                ToastUtil.showShort(listHttpResult.getMsg());
                                return Flowable.just(new ArrayList<>());
                            }
                        });
            }
        }, 10);

        mvcHelper.setDataSource(dataSource);
        mvcHelper.setAdapter(slimAdapterEx);
        mvcHelper.refresh();
    }

    @Override
    public void setView() {
//        DBUtil.getUser().observe(this, user -> {
//            OkGo.<HttpPageResult<Friend>>get(HttpConfig.BASE_URL + HttpConfig.FRIEND_PAGE_LIST + String.format("%d/%d", user.getId(), 0))
//                    .execute(new DialogCallBack<HttpPageResult<Friend>>(FriendListActivity.this) {
//                        @Override
//                        public void onSuccess(Response<HttpPageResult<Friend>> response) {
//                            HttpPageResult<Friend> httpResult = response.body();
//                            ToastUtil.showShort(httpResult.getMsg());
//                            if (httpResult.getStatus() == 200) {
//                                slimAdapterEx.updateData(httpResult.getItems().getContent());
//                            }
//                        }
//                    });
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestConfig.APP_ADD_FRIEND && resultCode == Config.SUCCESS) {
            mvcHelper.refresh();
        }
    }
}
