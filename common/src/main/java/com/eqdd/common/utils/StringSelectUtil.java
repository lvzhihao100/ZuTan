package com.eqdd.common.utils;

import android.support.annotation.ArrayRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eqdd.common.base.BaseActivity;

/**
 * @author吕志豪 .
 * @date 18-1-8  下午2:09.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class StringSelectUtil {
    public static MaterialDialog single(BaseActivity baseActivity, @ArrayRes int itemsRes, MaterialDialog.ListCallbackSingleChoice choose) {
        return new MaterialDialog.Builder(baseActivity)
                .title("单选")
                .items(itemsRes)
                //.listSelector(R.color.green)//列表的背景颜色
                .autoDismiss(true)//不自动消失
                .itemsCallbackSingleChoice(-1, choose)
                .build();
    }
}
