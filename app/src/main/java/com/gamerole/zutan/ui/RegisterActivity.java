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
import com.eqdd.common.bean.TwoBean;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.library.utils.HttpUtil;
import com.gamerole.zutan.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 吕志豪 on 17-10-17  下午3:13.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_REGISTER)
public class RegisterActivity extends CommonFullTitleActivity {

    private com.gamerole.zutan.RegisterActivityCustom dataBinding;
    private SlimAdapterEx<TwoBean<String, String>> slimAdapterEx;
    private String filepath;

    @Override
    protected void initBinding(ViewDataBinding inflate) {

        dataBinding = (com.gamerole.zutan.RegisterActivityCustom) inflate;
        initTopTitleBar("注册");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_register;
    }

    @Override
    public void initData() {

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapterEx = SlimAdapterEx.create().registerDefault(R.layout.library_list_item_14, new SlimInjector<TwoBean<String, String>>() {
            @Override
            public void onInject(TwoBean<String, String> data, IViewInjector injector) {

                injector.text(R.id.tv_left, data.getOne())
                        .text(R.id.tv_right, data.getTwo());
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());

    }

    @Override
    public void setView() {
        ClickUtil.click(dataBinding.rlCard, () -> {

            PicUtil.single(RegisterActivity.this);
        });
        ClickUtil.click(dataBinding.btSubmit, () -> {
            HttpUtil.getFaceTokenFromServer(RegisterActivity.this, slimAdapterEx.getDataItem(3).getTwo(), (status, object) -> {
                if (status == 200) {
                    String faceToken = (String) object;
                    register(faceToken);
                } else if (status == 201) {
                    HttpUtil.detectFace(RegisterActivity.this, new File(filepath), (isSuccess, faceToken) -> {
                        if (isSuccess) {
                            register(faceToken);
                        }
                    });
                } else {
                    hideLoading(R.string.COMMON_SERVER_ERROR);
                }
            });
        });
    }

    private void register(String faceToken) {
        showLoading("注册用户...");
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("name", slimAdapterEx.getDataItem(0).getTwo());
        maps.put("sex", slimAdapterEx.getDataItem(1).getTwo());
        maps.put("birth", slimAdapterEx.getDataItem(2).getTwo());
        maps.put("idCard", slimAdapterEx.getDataItem(3).getTwo());
        maps.put("race", slimAdapterEx.getDataItem(4).getTwo());
        maps.put("address", slimAdapterEx.getDataItem(5).getTwo());
        maps.put("password", slimAdapterEx.getDataItem(3).getTwo().substring(11, 18));
        maps.put("faceToken", faceToken);
        maps.put("isCheck", true);
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.REGISTER)
                .upJson(new JSONObject(maps))
                .retryCount(0)
                .execute(new JsonCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        HttpResult httpResult = response.body();
                        hideLoading(httpResult.getMsg());
                        if (httpResult.getStatus() == 200) {
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            filepath = localMedias.get(0).isCompressed() ?
                    localMedias.get(0).getCompressPath() : localMedias.get(0).getPath();
            HttpUtil.checkIDCard(RegisterActivity.this, new File(filepath), (isSuccess, cardsBean) -> {
                if (isSuccess) {
                    ArrayList<TwoBean<String, String>> slimData = new ArrayList<>();
                    slimData.add(new TwoBean<>("姓名", cardsBean.getName()));
                    slimData.add(new TwoBean<>("性别", cardsBean.getGender()));
                    slimData.add(new TwoBean<>("出生日期", cardsBean.getBirthday()));
                    slimData.add(new TwoBean<>("身份证号", cardsBean.getId_card_number()));
                    slimData.add(new TwoBean<>("民族", cardsBean.getRace()));
                    slimData.add(new TwoBean<>("家庭住址", cardsBean.getAddress()));
                    slimAdapterEx.updateData(slimData);
                    dataBinding.btSubmit.setVisibility(View.VISIBLE);
                    dataBinding.tvTip.setVisibility(View.VISIBLE);
                } else {
                    dataBinding.btSubmit.setVisibility(View.GONE);
                    dataBinding.tvTip.setVisibility(View.GONE);
                    ToastUtil.showShort("证件识别错误,请重新识别");
                }
            });
        }
    }
}
