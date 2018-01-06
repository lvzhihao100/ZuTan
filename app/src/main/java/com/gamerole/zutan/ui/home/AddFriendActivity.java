package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.bean.TwoBean;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.http.DialogCallBack;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.AddFriendActivityCustom;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ui.AddRelativeActivity;
import com.jakewharton.rxbinding.view.RxView;
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
 * @author吕志豪 .
 * @date 18-1-6  下午3:19.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ADD_FRIEND)
public class AddFriendActivity extends CommonFullTitleActivity {

    private AddFriendActivityCustom dataBinding;
    private SlimAdapterEx<TwoBean<String, String>> slimAdapterEx;
    private String filepath;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (AddFriendActivityCustom) inflate;
        initTopTitleBar("添加好友");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_add_friend;
    }

    @Override
    public void initData() {

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapterEx = SlimAdapterEx.create().register(R.layout.library_list_item_slim_edit_single, new SlimInjector<TwoBean>() {
            @Override
            public void onInject(TwoBean data, IViewInjector injector) {
                injector.text(R.id.tv_title, (String) data.getOne())
                        .text(R.id.et_content, (String) data.getTwo());
            }
        }).attachTo(dataBinding.recyclerView).updateData(new ArrayList());
        ArrayList<TwoBean<String, String>> data = new ArrayList<>();
        data.add(new TwoBean<>("*姓名", ""));
        data.add(new TwoBean<>("性别", ""));
        data.add(new TwoBean<>("出生日期", ""));
        data.add(new TwoBean<>("职业", ""));
        data.add(new TwoBean<>("民族", ""));
        data.add(new TwoBean<>("住址", ""));
        slimAdapterEx.updateData(data);
    }

    @Override
    public void setView() {
        RxView.clicks(dataBinding.rlCard)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    PicUtil.single(this);
                });
        ClickUtil.click(dataBinding.btSubmit, () -> {
            if (checkParams()) {
                OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.ADD_FRIEND)
                        .params("name", getContent(0))
                        .params("sex", getContent(1))
                        .params("birth", getContent(2))
                        .params("career", getContent(3))
                        .params("race", getContent(4))
                        .params("address", getContent(5))
                        .params("file", new File(filepath))
                        .execute(new DialogCallBack<HttpResult>(this) {
                            @Override
                            public void onSuccess(Response<HttpResult> response) {
                                HttpResult httpResult = response.body();
                                ToastUtil.showShort(httpResult.getMsg());
                                if (httpResult.getStatus() == 200) {
                                    finish();
                                }
                            }
                        });
            } else {
                ToastUtil.showShort("123");
            }
        });
    }

    @NonNull
    private String getContent(int pos) {
        return ((EditText) dataBinding.recyclerView.getChildAt(pos).findViewById(R.id.et_content)).getText().toString();
    }

    private boolean checkParams() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            filepath = localMedias.get(0).isCompressed() ?
                    localMedias.get(0).getCompressPath() : localMedias.get(0).getPath();
            ImageUtil.setImage(filepath, dataBinding.ivPhoto);
        }
    }
}
