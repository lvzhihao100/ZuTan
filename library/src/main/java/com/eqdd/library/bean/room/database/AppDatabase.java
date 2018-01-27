package com.eqdd.library.bean.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.eqdd.library.bean.room.User;
import com.eqdd.library.bean.room.Zu;
import com.eqdd.library.bean.room.dao.UserDao;
import com.eqdd.library.bean.room.dao.ZuDao;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:15.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Database(entities = {User.class, Zu.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserEntityDao();
    public abstract ZuDao getZuEntityDao();

}