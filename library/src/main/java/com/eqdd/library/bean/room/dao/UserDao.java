package com.eqdd.library.bean.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.eqdd.library.bean.room.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-1-8  上午11:21.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE idCard = (:idCard)")
    LiveData<User> getUserByIdCard(String idCard);

    @Query("SELECT * FROM user WHERE idCard = (:idCard)")
    Flowable<User> getUserByIdCardFlow(String idCard);

    @Query("SELECT * FROM user WHERE idCard = (:idCard)")
    User getUserByIdCardStatic(String idCard);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Delete()
    void deleteUser(User user);
}
