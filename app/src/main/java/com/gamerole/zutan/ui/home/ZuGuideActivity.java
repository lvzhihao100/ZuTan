package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.http.JsonConverter;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RequestConfig;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ZuGuideActivityCustom;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author吕志豪 .
 * @date 18-1-18  上午10:09.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ZU_GUIDE)
public class ZuGuideActivity extends CommonFullTitleActivity {

    private ZuGuideActivityCustom dataBinding;
    private long id;
    private Scene sceneFa;
    private Scene sceneMa;
    private Scene sceneOld;
    private Scene sceneSma;
    private Scene sceneSon;
    private boolean isImageBigger;
    private int primarySize;
    private boolean aBoolean;
    private boolean isEnable;
    private User applyToUser;
    private int relation;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (ZuGuideActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_zu_guide;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {
        dataBinding.appProgressbar.setVisibility(View.GONE);
        dataBinding.ivZuHead.setVisibility(View.GONE);
        dataBinding.tvNoExist.setVisibility(View.GONE);

        ClickUtil.click(dataBinding.create, () -> {
            ARouter.getInstance().build(RoutConfig.APP_ZU_CREATE).navigation(ZuGuideActivity.this, RequestConfig.APP_ZU_CREATE);

        });
        RxTextView.textChangeEvents(dataBinding.etZuId)
                .filter(textViewTextChangeEvent -> textViewTextChangeEvent.count() > 0)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> {
                    dataBinding.ivZuHead.setVisibility(View.GONE);
                    dataBinding.tvNoExist.setVisibility(View.GONE);
                    dataBinding.appProgressbar.setVisibility(View.VISIBLE);
                })
                .flatMap(textViewTextChangeEvent ->
                        OkGo.<HttpResult<Zu>>get(HttpConfig.BASE_URL + HttpConfig.APP_ZU_QUERY)
                                .params("id", Long.parseLong(textViewTextChangeEvent.text().toString()))
                                .converter(new JsonConverter<HttpResult<Zu>>() {
                                    @Override
                                    public void test() {
                                        super.test();
                                    }
                                })
                                .adapt(new ObservableBody<>()))
                .subscribe(zuHttpResult -> {
                    if (zuHttpResult.getItems() == null) {
                        dataBinding.ivZuHead.setVisibility(View.GONE);
                        dataBinding.tvNoExist.setVisibility(View.VISIBLE);
                        dataBinding.appProgressbar.setVisibility(View.GONE);
                    } else {
                        id = zuHttpResult.getItems().getId();
                        dataBinding.ivZuHead.setVisibility(View.VISIBLE);
                        dataBinding.tvNoExist.setVisibility(View.GONE);
                        dataBinding.appProgressbar.setVisibility(View.GONE);
                        ImageUtil.setCircleImage(zuHttpResult.getItems().getLogo(), dataBinding.ivZuHead);
                    }
                });
        ClickUtil.click(dataBinding.ivZuHead, () -> {
            ARouter.getInstance().build(RoutConfig.APP_HOME_LIST).withLong(Config.ID, id).navigation(ZuGuideActivity.this, RequestConfig.APP_USER_LIST);
        });
        ClickUtil.click(dataBinding.apply, () -> {
            OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.APP_ZU_APPLY_ENTER)
                    .params("applyTo", applyToUser.getIdCard())
                    .params("relationShip", relation)
                    .execute(new DialogCallBack<HttpResult>(ZuGuideActivity.this) {
                        @Override
                        public void onSuccess(Response<HttpResult> response) {
                            HttpResult httpResult = response.body();
                            if (httpResult.getStatus() == 200) {

                            }
                        }
                    });
        });
        DBUtil.getUserStatic(user -> {
            ImageUtil.setImage(user.getPhoto(), dataBinding.me);

        });
        dataBinding.wrapper.setVisibility(View.GONE);
//        Random random = new Random(System.currentTimeMillis());
//        aBoolean = random.nextBoolean();
//        System.out.println(aBoolean);
//        if (aBoolean) {
//            dataBinding.wrapper.setVisibility(View.GONE);
//            initScene();
//            clickScene();
//        } else {


//        }
    }

    @Override
    public void onClick(View v) {
        //start scene 是当前的scene
        TransitionManager.beginDelayedTransition(dataBinding.sceneRoot, TransitionInflater.from(this).inflateTransition(R.transition.app_explode_and_changebounds));
        //next scene 此时通过代码已改变了scene statue
        changeScene(v);
        switch (v.getId()) {
            case R.id.father:
                relation = Config.RELATION_FATHER;
                break;
            case R.id.mather:
                relation = Config.RELATION_MATHER;
                break;
            case R.id.old:
                relation = Config.RELATION_ELDER;
                break;
            case R.id.small:
                relation = Config.RELATION_YOUNGER;
                break;
            case R.id.son:
                relation = Config.RELATION_SON;
                break;
        }
    }

    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(dataBinding.father, dataBinding.mather, dataBinding.old, dataBinding.small, dataBinding.son);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * VISIBLE和INVISIBLE状态切换
     *
     * @param views
     */
    private void changeVisibility(View... views) {
        for (View view : views) {
            view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
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
            layoutParams.width = (int) (1.5 * primarySize);
            layoutParams.height = (int) (1.5 * primarySize);
        } else {
            layoutParams.width = primarySize;
            layoutParams.height = primarySize;
        }
        view.setLayoutParams(layoutParams);
    }


    private void clickScene() {
        ClickUtil.click(dataBinding.sceneRoot.findViewById(R.id.father), () -> {
            TransitionManager.go(sceneFa, new ChangeBounds());
            clickScene();
        });
        ClickUtil.click(dataBinding.sceneRoot.findViewById(R.id.mather), () -> {
            TransitionManager.go(sceneMa, new ChangeBounds());
            clickScene();
        });
        ClickUtil.click(dataBinding.sceneRoot.findViewById(R.id.old), () -> {
            TransitionManager.go(sceneOld, new ChangeBounds());
            clickScene();
        });
        ClickUtil.click(dataBinding.sceneRoot.findViewById(R.id.small), () -> {
            TransitionManager.go(sceneSma, new ChangeBounds());
            clickScene();
        });
        ClickUtil.click(dataBinding.sceneRoot.findViewById(R.id.son), () -> {
            TransitionManager.go(sceneSon, new ChangeBounds());
            clickScene();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Config.SUCCESS) {
            if (requestCode == RequestConfig.APP_ZU_CREATE) {
                setResult(Config.SUCCESS);
                finish();
            } else if (requestCode == RequestConfig.APP_USER_LIST) {
                applyToUser = (User) data.getSerializableExtra(Config.BEAN_SERIALIZABLE);
                dataBinding.wrapper.setVisibility(View.VISIBLE);
                primarySize = dataBinding.father.getLayoutParams().width;
                ImageUtil.setImage(applyToUser.getPhoto(), dataBinding.father);
                ImageUtil.setImage(applyToUser.getPhoto(), dataBinding.mather);
                ImageUtil.setImage(applyToUser.getPhoto(), dataBinding.old);
                ImageUtil.setImage(applyToUser.getPhoto(), dataBinding.small);
                ImageUtil.setImage(applyToUser.getPhoto(), dataBinding.son);
            }
        }
    }

    private void initScene() {
        sceneFa = Scene.getSceneForLayout(dataBinding.sceneRoot, R.layout.app_scene_father, this);
        sceneMa = Scene.getSceneForLayout(dataBinding.sceneRoot, R.layout.app_scene_mather, this);
        sceneOld = Scene.getSceneForLayout(dataBinding.sceneRoot, R.layout.app_scene_old, this);
        sceneSma = Scene.getSceneForLayout(dataBinding.sceneRoot, R.layout.app_scene_small, this);
        sceneSon = Scene.getSceneForLayout(dataBinding.sceneRoot, R.layout.app_scene_son, this);
        TransitionManager.go(sceneFa);
    }
}
