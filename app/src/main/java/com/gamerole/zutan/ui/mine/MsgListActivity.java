package com.gamerole.zutan.ui.mine;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.mvchelper.ModelRx2DataSource;
import com.eqdd.common.mvchelper.Rx2DataSource;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.RecyclerViewRefreshCustom;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.ApplyUserBean;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.library.utils.HttpUtil;
import com.gamerole.zutan.R;
import com.gamerole.zutan.utils.RelationUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.shizhefei.mvc.MVCCoolHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-2-7  下午5:36.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_MSG_LIST)
public class MsgListActivity extends CommonFullTitleActivity {
    private RecyclerViewRefreshCustom dataBinding;
    private MVCCoolHelper<List<ApplyUserBean>> mvcHelper;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (RecyclerViewRefreshCustom) inflate;
        initTopTitleBar("新消息");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview_refresh;
    }

    @Override
    public void initData() {

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapterEx slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_19, new SlimInjector<ApplyUserBean>() {
            @Override
            public void onInject(ApplyUserBean data, IViewInjector injector) {
                injector.imageCircle(R.id.iv_head, data.getApplyPhoto())
                        .text(R.id.tv_top, data.getApplyName())
                        .text(R.id.tv_bottom, "认证身份为你的" + RelationUtil.getShip(data.getRelation()))
                        .visibility(R.id.tv_right, data.getStatus().equals("申请中") ? View.GONE : View.VISIBLE)
                        .visibility(R.id.bt_right, data.getStatus().equals("申请中") ? View.VISIBLE : View.GONE)
                        .text(R.id.bt_right, "同意")
                        .text(R.id.tv_right, data.getStatus())
                        .clicked(R.id.bt_right, v -> {
                            showLoading("正在变更身份");
                            HttpUtil.agreeUserAuthApply(data.getId(), true, (status, object) -> {
                                if (status == 200) {
                                    hideLoading("操作成功");
                                    mvcHelper.refresh();
                                } else {
                                    hideLoading((String) object);
                                }
                            });
                        });
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
        ModelRx2DataSource<ApplyUserBean> dataSource = new ModelRx2DataSource<>(new ModelRx2DataSource.OnLoadSource() {
            @Override
            public Flowable<List> loadSource(int page, Rx2DataSource.DoneActionRegister register) {
                return OkGo.<HttpResult<List<ApplyUserBean>>>get(HttpConfig.BASE_URL + HttpConfig.APP_USER_APPLY_AUTH_LIST)
                        .params("page", page - 1)
                        .converter(new JsonConverter<HttpResult<List<ApplyUserBean>>>() {
                            @Override
                            public void test() {
                                super.test();
                            }
                        }).adapt(new FlowableBody<>())
                        .flatMap(listResultBean -> {
                            if (listResultBean.getStatus() == 200) {
                                return Flowable.just(listResultBean.getItems() == null ? new ArrayList<ApplyUserBean>() : listResultBean.getItems());
                            } else {
                                ToastUtil.showShort(listResultBean.getMsg());
                                return Flowable.just(new ArrayList<>());
                            }
                        });
            }
        });
        mvcHelper = new MVCCoolHelper<>(dataBinding.coolRefreshView);
        mvcHelper.setDataSource(dataSource);
        mvcHelper.setAdapter(slimAdapterEx);
        mvcHelper.refresh();
    }

    @Override
    public void setView() {


    }
}
