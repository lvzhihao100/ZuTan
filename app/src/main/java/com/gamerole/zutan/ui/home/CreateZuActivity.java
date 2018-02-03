package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eqdd.common.base.CommonFullTitleActivity;
import com.eqdd.common.http.DialogCallBack;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.library.base.Config;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.http.HttpConfig;
import com.eqdd.library.http.HttpResult;
import com.gamerole.zutan.R;
import com.gamerole.zutan.ZuCreateActivityCustom;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

/**
 * @author吕志豪 .
 * @date 18-1-17  下午5:08.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_ZU_CREATE)
public class CreateZuActivity extends CommonFullTitleActivity {

    private ZuCreateActivityCustom dataBinding;
    private String filePath;

    @Override
    protected void initBinding(ViewDataBinding inflate) {
        dataBinding = (ZuCreateActivityCustom) inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_zu_create;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {

        dataBinding.btSubmit.setEnabled(false);
        ClickUtil.click(dataBinding.btSubmit, () -> OkGo.<HttpResult<Long>>post(HttpConfig.BASE_URL + HttpConfig.APP_ZU_CREATE)
                .params("file", new File(filePath))
                .params("name", dataBinding.tvName.getText().toString().trim())
                .execute(new DialogCallBack<HttpResult<Long>>(CreateZuActivity.this) {
                    @Override
                    public void onSuccess(Response<HttpResult<Long>> response) {
                        HttpResult<Long> httpResult = response.body();
                        if (httpResult.getStatus() == 200) {
                            setResult(Config.SUCCESS);
                            finish();
                        }
                    }
                }));
        ClickUtil.click(dataBinding.ivHead, () -> PicUtil.single(CreateZuActivity.this));
        RxTextView.textChangeEvents(dataBinding.tvName)
                .subscribe(textViewTextChangeEvent -> {
                    if (textViewTextChangeEvent.text().length() >= 2 && filePath != null) {
                        dataBinding.btSubmit.setEnabled(true);
                    } else {
                        dataBinding.btSubmit.setEnabled(false);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
            filePath = localMedias.get(0).getCompressPath();
            if (dataBinding.tvName.getText().length() >= 2) {
                dataBinding.btSubmit.setEnabled(true);
            }
            ImageUtil.setCircleImage(new File(filePath), dataBinding.ivHead);
        }
    }
}
