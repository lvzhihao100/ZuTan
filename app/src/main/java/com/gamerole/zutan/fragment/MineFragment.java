package com.gamerole.zutan.fragment;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.BaseFragment;
import com.eqdd.common.box.ItemDecorate.SectionDividerLineItemDecoration;
import com.eqdd.common.utils.DensityUtil;
import com.eqdd.library.LibraryRecyclerViewCustom;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.number.SecondBean;
import com.eqdd.library.bean.number.ThirdBean;
import com.gamerole.zutan.R;

import java.util.ArrayList;

/**
 * @author吕志豪 .
 * @date 17-12-23  下午4:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class MineFragment extends BaseFragment {

    private LibraryRecyclerViewCustom dataBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.library_activity_recyclerview;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void initData() {

        dataBinding.includeTitle.commonTvTitle.setText("我");
        dataBinding.includeTitle.commonBack.setVisibility(View.GONE);
        ArrayList<Object> data = new ArrayList<>();
        data.add(new SecondBean<Integer, String>(R.mipmap.error_picture, "吕志豪"));
        data.add(new ThirdBean<Integer, String, String>(R.mipmap.error_picture, "我的钱包", "比特比 :0"));
        data.add(new ThirdBean<Integer, String, String>(R.mipmap.error_picture, "我的收益", "比特比 :0"));
        data.add(new ThirdBean<Integer, String, String>(R.mipmap.error_picture, "我的动态", "比特比 :0"));
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.recyclerView.addItemDecoration(new SectionDividerLineItemDecoration(getActivity(), 1, 3)
        .setLeftDividerPadding(DensityUtil.percentW(20))
        .setRightDividerPadding(0)
        .setSectionHeight(DensityUtil.percentW(12)));
        SlimAdapterEx.create().register(R.layout.library_list_item_17_head, new SlimInjector<SecondBean>() {
            @Override
            public void onInject(SecondBean data, IViewInjector injector) {

                injector.text(R.id.tv_name, (String) data.getTwo())
                        .imageCircle(R.id.iv_head, (Integer) data.getOne());
            }
        }).register(R.layout.library_list_item_18, new SlimInjector<ThirdBean>() {
            @Override
            public void onInject(ThirdBean data, IViewInjector injector) {
                injector.text(R.id.tv_left, (String) data.getTwo())
                        .text(R.id.tv_right, (String) data.getThree())
                        .image(R.id.iv_head, (Integer) data.getOne());
            }
        }).attachTo(dataBinding.recyclerView).updateData(data);

        ItemClickSupport.addTo(dataBinding.recyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    if (position == 0) {
                        ARouter.getInstance().build(RoutConfig.APP_ADD_SIMPLE_RELATIVE).navigation();
                    }
                });

    }

    @Override
    public ViewDataBinding initBinding(ViewDataBinding inflate) {
        return dataBinding = (LibraryRecyclerViewCustom) inflate;
    }

    @Override
    public void onClick(View v) {

    }
}
