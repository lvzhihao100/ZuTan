package com.eqdd.library.bean.room;

import android.arch.lifecycle.LiveData;

import com.eqdd.common.utils.SPUtil;
import com.eqdd.library.base.Config;

import io.reactivex.schedulers.Schedulers;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:17.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class DBUtil {
    public static void insertUser(User user) {
        Schedulers.io().scheduleDirect(() -> {
            DBHelper.getInstance().getDb().getUserEntityDao().addUser(user);
        });
    }

    public static LiveData<User> getUserByIdCard(String idCard) {
        return DBHelper.getInstance().getDb().getUserEntityDao().getUserByIdCard(idCard);
    }

    public static LiveData<User> getUser() {
        return getUserByIdCard((String) SPUtil.getParam(Config.IDCARD));
    }
}
