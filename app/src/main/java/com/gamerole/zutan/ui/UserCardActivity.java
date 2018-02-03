package com.gamerole.zutan.ui;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.box.ItemDecorate.DividerLineItemDecoration;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.DensityUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.bean.slim.SlimBean;
import com.eqdd.library.bean.slim.SlimEditBean;
import com.eqdd.library.bean.slim.SlimTextBean;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.gamerole.zutan.UserCardActivityCustom;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author吕志豪 .
 * @date 17-12-26  上午10:36.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_USER_CARD)
public class UserCardActivity extends CommonFullTitleActivity {

    private UserCardActivityCustom dataBinding;
    @Autowired
    long id;
    private SlimAdapterEx<SlimBean> slimAdapterEx;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (UserCardActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_user_card;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        ArrayList<SlimBean> slimBeans = new ArrayList<>();
        slimBeans.add(new SlimTextBean("名字:"));
        slimBeans.add(new SlimTextBean("性别:"));
        slimBeans.add(new SlimTextBean("生日:"));
        slimBeans.add(new SlimTextBean("城市:"));
        ClickUtil.click(dataBinding.ivHead, () -> PicUtil.single(this));
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerView.addItemDecoration(new DividerLineItemDecoration(this)
                .setFirstDraw(false)
                .setLastDraw(false)
                .setLeftPadding(DensityUtil.percentW(25)));
        slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_slim_edit_single, new SlimInjector<SlimEditBean>() {
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
        OkGo.<HttpResult<User>>get(HttpConfig.BASE_URL + HttpConfig.APP_GET_USER)
                .params("userId", id)
                .cacheKey(HttpConfig.BASE_URL + HttpConfig.RONG_GET_USER + id)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallBack<HttpResult<User>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<User>> response) {
                        HttpResult<User> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            User items = httpResult.getItems();
                            slimAdapterEx.getDataItem(0).setContent(items.getName());
                            slimAdapterEx.getDataItem(1).setContent(items.getSex());
                            slimAdapterEx.getDataItem(2).setContent(items.getBirth());
                            slimAdapterEx.getDataItem(3).setContent(items.getAddress());
                            slimAdapterEx.notifyItemRangeChanged(0, 4);
                            ImageUtil.setCircleImage(items.getCatongImg(), dataBinding.ivHead);
                            dataBinding.llHeadTop.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<HttpResult<User>> response) {
                        super.onCacheSuccess(response);
                        HttpResult<User> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            User items = httpResult.getItems();
                            slimAdapterEx.getDataItem(0).setContent(items.getName());
                            slimAdapterEx.getDataItem(1).setContent(items.getSex());
                            slimAdapterEx.getDataItem(2).setContent(items.getBirth());
                            slimAdapterEx.getDataItem(3).setContent(items.getAddress());
                            slimAdapterEx.notifyItemRangeChanged(0, 4);
                            ImageUtil.setCircleImage(items.getCatongImg(), dataBinding.ivHead);
                            dataBinding.llHeadTop.setVisibility(View.GONE);

                        }
                    }
                });
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
