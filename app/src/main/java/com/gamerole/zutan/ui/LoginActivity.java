package com.gamerole.zutan.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.common.utils.SPUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.databind.percent.WindowUtil;
import com.eqdd.inputs.AndroidNextInputs;
import com.eqdd.inputs.WidgetAccess;
import com.eqdd.library.Iservice.rongtalk.RongConnectService;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.eqdd.common.http.JsonCallBack;
import com.eqdd.library.utils.HttpUtil;
import com.eqdd.nextinputs.StaticScheme;
import com.eqdd.nextinputs.ValueScheme;
import com.gamerole.zutan.LoginActivityCustom;
import com.gamerole.zutan.R;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 吕志豪 on 17-10-13  下午2:48.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_LOGIN)
public class LoginActivity extends CommonActivity {

    private LoginActivityCustom dataBinding;
    private boolean isPassShow = false;
    private boolean isUp;
    private AndroidNextInputs inputs;
    private WidgetAccess access;
    private AndroidNextInputs inputsAll;
    private String idCardPath;
    @Autowired
    RongConnectService rongConnectService;
    private String token;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_login);
        ImageUtil.flurImage(R.mipmap.app_login_bg, dataBinding.ivBg, 25);
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        inputs = new AndroidNextInputs();
        inputsAll = new AndroidNextInputs();
        access = new WidgetAccess(this);
        inputs  // 必选，手机号
                .add(access.findEditText(R.id.et_username))
                .with(StaticScheme.Required(), StaticScheme.ChineseIDCard());
        inputsAll  // 必选，手机号
                .add(access.findEditText(R.id.et_username))
                .with(StaticScheme.Required(), StaticScheme.ChineseIDCard())
                .add(access.findEditText(R.id.et_password))
                .with(StaticScheme.Required(), ValueScheme.RangeLength(6, 18));

//        RxPermissions.getInstance(LoginActivity.this)
//                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_PHONE_STATE)
//                .subscribe(isGranted -> {
//                    if (isGranted) {
//                        ARouter.getInstance().build(RoutConfig.APP_SHOW_MAP).navigation();
//                    } else {
//                        PermissionTipUtil.tip(LoginActivity.this, "位置");
//                    }
//                });
    }

    @Override
    public void setView() {
//        RxPermissions.getInstance(LoginActivity.this)
//                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_PHONE_STATE)
//                .subscribe(isGranted -> {
//                    if (isGranted) {
//                        ARouter.getInstance().build(RoutConfig.APP_SHOW_MAP).navigation();
//                    } else {
//                        PermissionTipUtil.tip(LoginActivity.this, "位置");
//                    }
//                });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int screenHeight = metrics.heightPixels;
        ClickUtil.click(dataBinding.ivEye, () -> {
            if (!isPassShow) {
                isPassShow = true;
                dataBinding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                dataBinding.ivEye.setImageResource(R.mipmap.library_ic_eye_open);
            } else {
                isPassShow = false;
                dataBinding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                dataBinding.ivEye.setImageResource(R.mipmap.library_ic_eye_hint);
            }
        });
        dataBinding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect(); //该对象代表一个矩形（rectangle）
            dataBinding.rlRoot.getWindowVisibleDisplayFrame(r); //将当前界面的尺寸传给Rect矩形
            int deltaHeight = screenHeight - r.bottom;  //弹起键盘时的变化高度，在该场景下其实就是键盘高度。
            if (deltaHeight > 350 * WindowUtil.csw / WindowUtil.width && !isUp) {
                //该值是随便写的，认为界面高度变化超过150px就视为键盘弹起。
                isUp = true;
                dataBinding.tvLogo.setVisibility(View.VISIBLE);
                ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(animation -> {
                    float curValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = dataBinding.llLogo.getLayoutParams();
                    layoutParams.height = (int) (300 - (3 * curValue)) * WindowUtil.csw / WindowUtil.width;
                    layoutParams.width = (int) (300 - (3 * curValue)) * WindowUtil.csw / WindowUtil.width;
                    dataBinding.llLogo.setLayoutParams(layoutParams);
                    dataBinding.llLogo.setAlpha((float) (0.01 * (100 - curValue)));
                    dataBinding.tvLogo.setAlpha((float) (0.01 * curValue));
                });
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dataBinding.llLogo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();


            } else if (deltaHeight < 350 * WindowUtil.csw / WindowUtil.width && isUp) {
                isUp = false;
                dataBinding.llLogo.setVisibility(View.VISIBLE);
                ValueAnimator animator = ValueAnimator.ofFloat(100, 0);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(animation -> {
                    float curValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = dataBinding.llLogo.getLayoutParams();
                    layoutParams.height = (int) (300 - (3 * curValue)) * WindowUtil.csw / WindowUtil.width;
                    layoutParams.width = (int) (300 - (3 * curValue)) * WindowUtil.csw / WindowUtil.width;
                    dataBinding.llLogo.setLayoutParams(layoutParams);
                    dataBinding.llLogo.setAlpha((float) (0.01 * (100 - curValue)));
                    dataBinding.tvLogo.setAlpha((float) (0.01 * curValue));
                });
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dataBinding.tvLogo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        });
        RxView.clicks(dataBinding.llLogo)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> PicUtil.single(LoginActivity.this));
        RxView.clicks(dataBinding.btPhoto)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    PicUtil.camera(LoginActivity.this);
                });
        RxView.longClicks(dataBinding.llLogo)
                .subscribe(aVoid -> {
                    ARouter.getInstance().build(RoutConfig.APP_REGISTER).navigation();
                });
        RxView.clicks(dataBinding.btLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (inputsAll.test()) {
                        requestLogin();


                    }
                });
        dataBinding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 18) {
                    if (inputs.test()) {
                        dataBinding.btPhoto.setEnabled(true);
                        return;
                    }
                }
                dataBinding.btPhoto.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void requestLogin() {
        OkGo.<HttpResult<User>>post(HttpConfig.BASE_URL + HttpConfig.LOGIN)
                .params("idCard", dataBinding.etUsername.getText().toString())
                .params("password", dataBinding.etPassword.getText().toString())
                .execute(new DialogCallBack<HttpResult<User>>(LoginActivity.this) {
                    @Override
                    public void onSuccess(Response<HttpResult<User>> response) {
                        HttpResult<User> httpResult = response.body();
                        ToastUtil.showShort(httpResult.getMsg());
                        if (httpResult.getStatus() == 200) {
                            rongConnectAndLocalSave(httpResult.getItems());
                        }
                    }
                });
    }

    private void rongConnectAndLocalSave(User httpUser) {
        DBUtil.insertUser(httpUser);
        SPUtil.setParam(Config.IDCARD, httpUser.getIdCard());
        DBUtil.getUserStatic(user -> {
            if (user != null) {
                if (!TextUtils.isEmpty(user.getToken())) {
                    System.out.println("使用本地token" + token);
                    rongConnectService.getToken(token, (token1, isSuccess) -> {
                        if (isSuccess) {
                            user.setToken(token1);
                            DBUtil.insertUser(user);
                            JPushInterface.setAlias(getApplicationContext(), 0, user.getIdCard());
                            ARouter.getInstance().build(RoutConfig.APP_HOME).navigation();
                            finish();

                        }
                    });
                } else {
                    System.out.println("获取服务器token中。。。");
                    OkGo.<HttpResult<String>>post(HttpConfig.BASE_URL + HttpConfig.GET_RONG_TOKEN)
                            .execute(new JsonCallBack<HttpResult<String>>() {
                                @Override
                                public void onSuccess(Response<HttpResult<String>> response) {
                                    HttpResult<String> httpResult = response.body();

                                    if (httpResult.getStatus() == 200) {
                                        if (httpResult.getItems() != null) {
                                            token = httpResult.getItems();
                                            System.out.println("获取到token");
                                            rongConnectService.getToken(token, (token1, isSuccess) -> {
                                                user.setToken(token1);
                                                DBUtil.insertUser(user);
                                                ARouter.getInstance().build(RoutConfig.APP_HOME).navigation();
                                                finish();
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onError(Response<HttpResult<String>> response) {
                                    super.onError(response);
                                }
                            });

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            idCardPath = localMedias.get(0).isCompressed() ?
                    localMedias.get(0).getCompressPath() : localMedias.get(0).getPath();
            HttpUtil.checkIDCard(LoginActivity.this, new File(idCardPath), (isSuccess, cardsBean) -> {
                if (isSuccess) {
                    dataBinding.etUsername.setText(cardsBean.getId_card_number());
                } else {
                    ToastUtil.showShort("证件识别错误,请重新识别");
                }
            });
        }
        if (resultCode == RESULT_OK && requestCode == PictureConfig.REQUEST_CAMERA) {
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            String filePath = localMedias.get(0).isCompressed() ?
                    localMedias.get(0).getCompressPath() : localMedias.get(0).getPath();
            showLoading("正在校验身份...");
            OkGo.<HttpResult<User>>post(HttpConfig.BASE_URL + HttpConfig.QUERY_USER_BY_IDCARD)
                    .params("idCard", dataBinding.etUsername.getText().toString())
                    .params("file", new File(filePath))
                    .execute(new JsonCallBack<HttpResult<User>>() {
                        @Override
                        public void onSuccess(Response<HttpResult<User>> response) {
                            HttpResult<User> httpResult = response.body();
                            if (httpResult.getStatus() == 200) {
                                hideLoading("校验成功,正在登录...");
                                rongConnectAndLocalSave(httpResult.getItems());
                            } else {
                                hideLoading(httpResult.getMsg());
                            }
                        }

                        @Override
                        public void onError(Response<HttpResult<User>> response) {
                            super.onError(response);
                            hideLoading(R.string.COMMON_SERVER_ERROR);
                        }
                    });
        }
    }

}
