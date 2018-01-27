package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RequestConfig;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpPageResult;
import com.gamerole.zutan.HomeListActivityCustom;
import com.gamerole.zutan.R;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.shizhefei.mvc.MVCCoolHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-1-10  下午3:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_HOME_LIST)
public class HomeListActivity extends CommonActivity {


    private SlimAdapterEx<User> slimAdapterEx;
    private HomeListActivityCustom dataBinding;
    private MVCCoolHelper<List<User>> mvcHelper;
    private ModelRx2DataSource<User> dataSource;
    private int pageNum;
    @Autowired
    long id;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_home_list);
//        ClickUtil.click(dataBinding.tvAdd, () -> {
//            ARouter.getInstance().build(RoutConfig.APP_ADD_FRIEND).navigation();
//        });
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.library_list_divider));
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
                    if (id > 0) {
                        Intent intent = new Intent();
                        intent.putExtra(Config.BEAN_SERIALIZABLE, slimAdapterEx.getDataItem(position));
                        setResult(Config.SUCCESS, intent);
                        finish();
                    } else {
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeListActivity.this
                                , new Pair(v.findViewById(R.id.iv_poster), "shared_image_")
                                , new Pair(v.findViewById(R.id.tv_name), "shared_text_"));
//
                        ARouter.getInstance()
                                .build(RoutConfig.APP_USER_INFO)
                                .withObject("user", slimAdapterEx.getDataItem(position))
                                .withOptionsCompat(activityOptionsCompat)
                                .navigation(HomeListActivity.this);
                    }
                });
        mvcHelper = new MVCCoolHelper<>(dataBinding.coolRefreshView);
        dataSource = new ModelRx2DataSource<>(new ModelRx2DataSource.OnLoadSource() {
            @Override
            public Flowable<List> loadSource(int page, Rx2DataSource.DoneActionRegister register) {
                pageNum = page - 1;
                return OkGo.<HttpPageResult<User>>get(HttpConfig.BASE_URL + HttpConfig.ZU_USER_PAGE_LIST)
                        .params("page", pageNum)
                        .params("zuId", id)
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
    public void setView() {
        DBUtil.getUserStatic(o -> {
            if (o.getZuId() <= 0 && id == 0) {
                ARouter.getInstance().build(RoutConfig.APP_ZU_GUIDE).navigation(HomeListActivity.this, RequestConfig.APP_ZU_GUIDE);
            } else {
                mvcHelper.refresh();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestConfig.APP_ZU_GUIDE) {
            if (resultCode == Config.SUCCESS) {
                mvcHelper.refresh();
            } else {
                finish();
            }
        }
    }
}
