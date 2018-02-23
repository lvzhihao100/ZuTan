package com.gamerole.zutan.ui.mine;

import android.databinding.ViewDataBinding;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.AuthRelationActivityCustom;
import com.gamerole.zutan.R;
import com.gamerole.zutan.UserItemActivityCustom;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @author吕志豪 .
 * @date 18-2-7  下午4:47.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_AUTH_RELATIVE)
public class AuthRelationActivity extends CommonFullTitleActivity {

    private AuthRelationActivityCustom dataBinding;
    @Autowired
    long id;
    private int relation;
    private boolean isImageBigger;
    private int primarySize;
    private String idCard;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (AuthRelationActivityCustom) inflate;
        initTopTitleBar("关系认证");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_auth_relation;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        animClick(dataBinding.includeFather.getRoot(), Config.RELATION_FATHER);
        animClick(dataBinding.includeMather.getRoot(), Config.RELATION_MATHER);
        animClick(dataBinding.includeAnother.getRoot(), Config.RELATION_ANOTHER);
        animClick(dataBinding.includeSon.getRoot(), Config.RELATION_SON);
        animClick(dataBinding.includeOld.getRoot(), Config.RELATION_ELDER);
        animClick(dataBinding.includeSmall.getRoot(), Config.RELATION_YOUNGER);
    }

    @Override
    public void onClick(View v) {

    }

    private void animClick(View view, int relation) {
        ClickUtil.click(view, () -> {
            //start scene 是当前的scene
            TransitionManager.beginDelayedTransition(dataBinding.sceneRoot, TransitionInflater.from(this).inflateTransition(R.transition.app_explode_and_changebounds));
            //next scene 此时通过代码已改变了scene statue
            changeScene(view);
            this.relation = relation;

        });
    }

    private void changeScene(View view) {
        changeSize(view.findViewById(R.id.circleImageView));
        changeVisibility(dataBinding.includeFather, dataBinding.includeMather, dataBinding.includeAnother, dataBinding.includeSon, dataBinding.includeOld, dataBinding.includeSmall);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * VISIBLE和INVISIBLE状态切换
     *
     * @param views
     */
    private void changeVisibility(UserItemActivityCustom... views) {
        for (UserItemActivityCustom view : views) {
            view.getRoot().setVisibility(view.getRoot().getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        }
        dataBinding.apply.setEnabled(!dataBinding.apply.isEnabled());
    }

    /**
     * view的宽高1.5倍和原尺寸大小切换
     * 配合ChangeBounds实现缩放效果
     *
     * @param view
     */
    private void changeSize(View view) {
        isImageBigger = !isImageBigger;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (isImageBigger) {
            layoutParams.width = primarySize;
            layoutParams.height = primarySize;
        } else {
            layoutParams.width = primarySize * 2 / 3;
            layoutParams.height = primarySize * 2 / 3;
        }
        view.setLayoutParams(layoutParams);
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
        ClickUtil.click(dataBinding.apply, () -> OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.APP_USER_APPLY_AUTH)
                .params("applyTo", idCard)
                .params("relation", relation)
                .retryCount(0)
                .execute(new DialogCallBack<HttpResult>(AuthRelationActivity.this) {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        HttpResult httpResult = response.body();
                        ToastUtil.showShort(httpResult.getMsg());
                        if (httpResult.getStatus() == 200) {
                            finish();
                        }
                    }
                }));
        OkGo.<HttpResult<User>>get(HttpConfig.BASE_URL + HttpConfig.APP_GET_USER)
                .params("userId", id)
                .execute(new JsonCallBack<HttpResult<User>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<User>> response) {
                        HttpResult<User> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            primarySize = dataBinding.includeFather.getRoot().getLayoutParams().width;
                            updateView(httpResult.getItems());
                        }
                    }
                });

    }

    private void updateView(User items) {
        idCard = items.getIdCard();
        ImageUtil.setImage(items.getCatongImg(), dataBinding.includeFather.circleImageView);
        ImageUtil.setImage(items.getCatongImg(), dataBinding.includeMather.circleImageView);
        ImageUtil.setImage(items.getCatongImg(), dataBinding.includeAnother.circleImageView);
        ImageUtil.setImage(items.getCatongImg(), dataBinding.includeSon.circleImageView);
        ImageUtil.setImage(items.getCatongImg(), dataBinding.includeOld.circleImageView);
        ImageUtil.setImage(items.getCatongImg(), dataBinding.includeSmall.circleImageView);
        dataBinding.includeFather.tvName.setText(items.getName());
        dataBinding.includeMather.tvName.setText(items.getName());
        dataBinding.includeAnother.tvName.setText(items.getName());
        dataBinding.includeSon.tvName.setText(items.getName());
        dataBinding.includeOld.tvName.setText(items.getName());
        dataBinding.includeSmall.tvName.setText(items.getName());
    }
}
