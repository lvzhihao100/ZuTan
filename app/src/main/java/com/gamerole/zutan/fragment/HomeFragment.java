package com.gamerole.zutan.fragment;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.BaseFragment;
import com.eqdd.common.box.ItemDecorate.SectionDividerLineItemDecoration;
import com.eqdd.common.utils.DensityUtil;
import com.eqdd.library.Iservice.rongtalk.RongStartService;
import com.eqdd.library.LibraryOnlyRecyclerViewCustom;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.bean.number.SecondBean;
import com.eqdd.library.bean.number.ThirdBean;
import com.eqdd.library.bean.room.DBUtil;
import com.gamerole.zutan.R;

import java.util.ArrayList;

/**
 * @author吕志豪 .
 * @date 17-12-23  下午4:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class HomeFragment extends BaseFragment {

    private LibraryOnlyRecyclerViewCustom dataBinding;

    @Autowired
    RongStartService rongStartService;
    private Zu zu;

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_only_recyclerview;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        ArrayList<Object> data = new ArrayList<>();
        data.add(new ThirdBean(R.mipmap.error_picture, "我的家族", ""));
        data.add(new ThirdBean(R.mipmap.error_picture, "我的友圈", ""));
        data.add(new ThirdBean(R.mipmap.error_picture, "新成员", ""));
        data.add(new ThirdBean(R.mipmap.error_picture, "搜索", ""));
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.recyclerView.addItemDecoration(new SectionDividerLineItemDecoration(getActivity(), 1, 3)
                .setLeftDividerPadding(DensityUtil.percentW(20))
                .setRightDividerPadding(0)
                .setSectionHeight(DensityUtil.percentW(12)));
        SlimAdapterEx slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_17_head, new SlimInjector<SecondBean>() {
            @Override
            public void onInject(SecondBean data, IViewInjector injector) {

                injector.text(R.id.tv_name, (String) data.getTwo())
                        .imageCircle(R.id.iv_head, data.getOne());
            }
        }).register(R.layout.library_list_item_18, new SlimInjector<ThirdBean>() {
            @Override
            public void onInject(ThirdBean data, IViewInjector injector) {
                injector.text(R.id.tv_left, (String) data.getTwo())
                        .text(R.id.tv_right, (String) data.getThree())
                        .imageCircle(R.id.iv_head, data.getOne());
            }
        }).attachTo(dataBinding.recyclerView).updateData(data);
        ItemClickSupport.addTo(dataBinding.recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    if (position == 0) {
                        ARouter.getInstance().build(RoutConfig.APP_ZU_LIST).navigation();
                    } else if (position == 1) {
                        ARouter.getInstance().build(RoutConfig.APP_FRIEND_LIST).navigation();
                    } else if (position == 2) {
                        ARouter.getInstance().build(RoutConfig.APP_ZU_APPLY_LIST).navigation();
                    } else if (position == 3) {
                        ARouter.getInstance().build(RoutConfig.APP_SEARCH_ZU).navigation();
                    }
                });
//        DBUtil.getUserStatic(user -> OkGo.<HttpResult<Zu>>get(HttpConfig.BASE_URL + HttpConfig.APP_ZU_QUERY)
//                .params("id", user.getZuId())
//                .execute(new JsonCallBack<HttpResult<Zu>>() {
//                    @Override
//                    public void onSuccess(Response<HttpResult<Zu>> response) {
//                        HttpResult<Zu> httpResult = response.body();
//                        if (httpResult.getStatus() == 200) {
//
//                        }
//                    }
//                }));
        DBUtil.getZuLiveData().observe(this, zu -> {
            if (zu != null) {
                HomeFragment.this.zu = zu;
                ThirdBean dataItem = (ThirdBean) slimAdapterEx.getDataItem(0);
                dataItem.setOne(zu.getLogo());
                slimAdapterEx.notifyItemChanged(0);
            }
        });

    }

    @Override
    public ViewDataBinding initBinding(ViewDataBinding inflate) {
        return dataBinding = (LibraryOnlyRecyclerViewCustom) inflate;
    }

    @Override
    public void onClick(View v) {

    }
}
