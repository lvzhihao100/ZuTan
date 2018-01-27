package com.eqdd.library.bean.room;

import android.arch.lifecycle.LiveData;

import com.eqdd.common.utils.SPUtil;
import com.eqdd.library.base.Config;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public static void insertZu(Zu zu) {
        Schedulers.io().scheduleDirect(() -> {
            DBHelper.getInstance().getDb().getZuEntityDao().addZu(zu);
        });
    }

    public static LiveData<User> getUserByIdCard(String idCard) {
        return DBHelper.getInstance().getDb().getUserEntityDao().getUserByIdCard(idCard);
    }

    private static Flowable<User> getUserByIdCardStatic(String idCard) {
        return DBHelper.getInstance().getDb().getUserEntityDao().getUserByIdCardFlow(idCard);
    }

    public static LiveData<User> getUser() {
        return getUserByIdCard(SPUtil.getParam(Config.IDCARD, ""));
    }

    public static Flowable<List<User>> getAllUser() {
        return DBHelper.getInstance().getDb().getUserEntityDao().getAll();
    }

    public static Flowable<User> getUserFlow() {
        return getUserByIdCardStatic(SPUtil.getParam(Config.IDCARD, ""));
    }

    public static void getUserStatic(UserBack userBack) {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map(integer -> DBHelper.getInstance().getDb().getUserEntityDao().getUserByIdCardStatic(SPUtil.getParam(Config.IDCARD, "")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> userBack.back(user));
    }

    public static LiveData<Zu> getZuLiveData() {
        return DBHelper.getInstance().getDb().getZuEntityDao().getZuLiveData();
    }

    public static void getZu(ZuBack zuBack) {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map(integer -> DBHelper.getInstance().getDb().getZuEntityDao().getZu())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zu -> zuBack.back(zu));
    }

    public interface UserBack {
        void back(User user);
    }

    public interface ZuBack {
        void back(Zu zu);
    }
}
