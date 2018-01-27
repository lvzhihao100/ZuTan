package com.eqdd.library.bean.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.eqdd.library.bean.room.Zu;

import io.reactivex.Flowable;

/**
 * @author吕志豪 .
 * @date 18-1-8  上午11:21.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Dao
public interface ZuDao {
    @Query("SELECT * FROM zu limit 1")
    Flowable<Zu> getZuFlow();

    @Query("SELECT * FROM zu limit 1")
    LiveData<Zu> getZuLiveData();

    @Query("SELECT * FROM zu limit 1")
    Zu getZu();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addZu(Zu zu);

    @Delete()
    void delete(Zu zu);
}
