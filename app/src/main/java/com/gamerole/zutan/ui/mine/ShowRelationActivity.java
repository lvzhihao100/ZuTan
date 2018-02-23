package com.gamerole.zutan.ui.mine;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ShowRelationActivityCustom;
import com.gamerole.zutan.UserItemActivityCustom;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * @author吕志豪 .
 * @date 18-2-7  下午3:54.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_SHOW_RELATION)
public class ShowRelationActivity extends CommonFullTitleActivity {
    @Autowired
    long id;

    private ShowRelationActivityCustom dataBinding;
    private HttpResult<List<User>> httpResult;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (ShowRelationActivityCustom) inflate;
        initTopTitleBar("关系图");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_show_relation;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        refresh();
    }

    @Override
    public void setView() {

        dataBinding.includeFather.tvType.setText("爸爸");
        dataBinding.includeMather.tvType.setText("妈妈");
        dataBinding.includeAnother.tvType.setText("另一半");
        dataBinding.includeMe.tvType.setText("我");
        dataBinding.includeSon.tvType.setText("长子");
        dataBinding.includeOld.tvType.setText("兄/姐");
        dataBinding.includeSmall.tvType.setText("妹/弟");
        ClickUtil.click(dataBinding.includeFather.circleImageView, () -> {
            if (httpResult.getItems().get(0) != null) {
                id = httpResult.getItems().get(0).getId();
                refresh();
            }
        });
        ClickUtil.click(dataBinding.includeMather.circleImageView, () -> {
            if (httpResult.getItems().get(1) != null) {
                id = httpResult.getItems().get(1).getId();
                refresh();
            }
        });
        ClickUtil.click(dataBinding.includeAnother.circleImageView, () -> {
            if (httpResult.getItems().get(2) != null) {
                id = httpResult.getItems().get(2).getId();
                refresh();
            }
        });
        ClickUtil.click(dataBinding.includeSon.circleImageView, () -> {
            if (httpResult.getItems().get(4) != null) {
                id = httpResult.getItems().get(4).getId();
                refresh();
            }
        });
        ClickUtil.click(dataBinding.includeOld.circleImageView, () -> {
            if (httpResult.getItems().get(5) != null) {
                id = httpResult.getItems().get(5).getId();
                refresh();
            }
        });
        ClickUtil.click(dataBinding.includeSmall.circleImageView, () -> {
            if (httpResult.getItems().get(6) != null) {
                id = httpResult.getItems().get(6).getId();
                refresh();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        ARouter.getInstance().inject(this);
        System.out.println(id);
        refresh();
    }

    private void refresh() {
        System.out.println(id);
        OkGo.<HttpResult<List<User>>>post(HttpConfig.BASE_URL + HttpConfig.APP_USER_RELATIVE)
                .params("id", id)
                .execute(new DialogCallBack<HttpResult<List<User>>>(ShowRelationActivity.this) {
                    @Override
                    public void onSuccess(Response<HttpResult<List<User>>> response) {
                        httpResult = response.body();
                        ToastUtil.showShort(httpResult.getMsg());
                        if (httpResult.getStatus() == 200) {
                            updateView(httpResult.getItems());
                        }
                    }
                });
    }

    private void updateView(List<User> items) {
        updateItem(dataBinding.includeFather, items.get(0));
        updateItem(dataBinding.includeMather, items.get(1));
        updateItem(dataBinding.includeAnother, items.get(2));
        updateItem(dataBinding.includeMe, items.get(3));
        updateItem(dataBinding.includeSon, items.get(4));
        updateItem(dataBinding.includeOld, items.get(5));
        updateItem(dataBinding.includeSmall, items.get(6));
    }

    private void updateItem(UserItemActivityCustom include, User user) {
        if (user == null) {
            ImageUtil.setImage(R.mipmap.common_holder_img, include.circleImageView);
            include.tvName.setText("");
        } else {
            ImageUtil.setImage(user.getCatongImg(), include.circleImageView);
            include.tvName.setText(user.getName());
        }
    }
}
