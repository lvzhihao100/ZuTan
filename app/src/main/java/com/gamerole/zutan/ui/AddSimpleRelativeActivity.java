package com.gamerole.zutan.ui;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.box.ItemDecorate.DividerLineItemDecoration;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.DensityUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.slim.SlimBean;
import com.eqdd.library.bean.slim.SlimEditBean;
import com.eqdd.library.bean.slim.SlimTextBean;
import com.gamerole.zutan.AddSimpleRelativeActivityCustom;
import com.gamerole.zutan.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author吕志豪 .
 * @date 17-12-26  上午10:36.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ADD_SIMPLE_RELATIVE)
public class AddSimpleRelativeActivity extends CommonFullTitleActivity {

    private AddSimpleRelativeActivityCustom dataBinding;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (AddSimpleRelativeActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_add_simple_relative;
    }

    @Override
    public void initData() {
        ArrayList<SlimBean> slimBeans = new ArrayList<>();
        slimBeans.add(new SlimEditBean("名字"));
        slimBeans.add(new SlimEditBean("性别"));
        slimBeans.add(new SlimEditBean("生日"));
        slimBeans.add(new SlimEditBean("城市"));
        ClickUtil.click(dataBinding.ivHead, () -> {
            PicUtil.single(this);
        });
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerView.addItemDecoration(new DividerLineItemDecoration(this)
                .setFirstDraw(false)
        .setLastDraw(false)
        .setLeftPadding(DensityUtil.percentW(25)));
        SlimAdapterEx.create().register(R.layout.library_list_item_slim_edit_single, new SlimInjector<SlimEditBean>() {
            @Override
            public void onInject(SlimEditBean data, IViewInjector injector) {
                injector.text(R.id.tv_title, data.getTitle())
                        .text(R.id.et_content, data.getContent());
            }
        }).register(R.layout.library_list_item_slim_text, new SlimInjector<SlimTextBean>() {
            @Override
            public void onInject(SlimTextBean data, IViewInjector injector) {
                injector.text(R.id.tv_title, data.getTitle())
                        .text(R.id.et_content, data.getContent());
            }
        }).attachTo(dataBinding.recyclerView).updateData(slimBeans);


    }

    @Override
    public void setView() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            dataBinding.llHeadTop.setVisibility(View.GONE);
            ImageUtil.setCircleImage(localMedias.get(0).getCompressPath(), dataBinding.ivHead);
        }
    }
}
