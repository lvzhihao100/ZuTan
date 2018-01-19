package com.gamerole.zutan.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.common.utils.StringSelectUtil;
import com.eqdd.common.utils.ToastUtil;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.AddFriendActivityCustom;
import com.gamerole.zutan.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    private String filepath;
    private String msg;
    private com.afollestad.materialdialogs.MaterialDialog sexChoose;
    private DatePickerDialog dateChoose;
    private MaterialDialog raceChoose;

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

    }

    @Override
    public void setView() {
        ClickUtil.click(dataBinding.rlCard,() -> {
            PicUtil.cut(this, 640, 412);
        });

        ClickUtil.click(dataBinding.btSubmit, () -> {
            if (checkParams()) {
                OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.ADD_FRIEND)
                        .params("name", dataBinding.etName.getText().toString())
                        .params("sex", dataBinding.etSex.getText().toString())
                        .params("birth", dataBinding.etBirth.getText().toString())
                        .params("career", dataBinding.etCareer.getText().toString())
                        .params("race", dataBinding.etRace.getText().toString())
                        .params("address", dataBinding.etAddress.getText().toString())
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
                ToastUtil.showShort(msg);
            }
        });
        ClickUtil.click(dataBinding.etSex, () -> {
            if (sexChoose == null) {
                sexChoose = StringSelectUtil.single(this, R.array.common_sex, (dialog, itemView, which, text) -> {
                    dataBinding.etSex.setText(text);
                    return true;
                });
            }
            sexChoose.show();
        });
        ClickUtil.click(dataBinding.etBirth, () -> {
            if (dateChoose == null) {
                dateChoose = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                    Calendar instance = Calendar.getInstance();
                    instance.set(Calendar.YEAR, year);
                    instance.set(Calendar.MONTH, month);
                    instance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
                    dataBinding.etBirth.setText(date);
                }, -1, -1, -1);
            }
            dateChoose.show();
        });
        ClickUtil.click(dataBinding.etRace, () -> {
            if (raceChoose == null) {
                raceChoose = StringSelectUtil.single(this, R.array.common_sex, (dialog, itemView, which, text) -> {
                    dataBinding.etRace.setText(text);
                    return true;
                });
            }
            raceChoose.show();
        });
    }

    private boolean checkParams() {
        if (TextUtils.isEmpty(filepath)) {
            msg = "头像必传";
            return false;
        }
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
