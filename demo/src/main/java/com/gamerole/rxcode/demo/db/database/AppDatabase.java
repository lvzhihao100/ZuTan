package com.gamerole.rxcode.demo.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gamerole.rxcode.demo.db.dao.StudentDao;
import com.gamerole.rxcode.demo.db.entity.Student;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:15.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao getStudentEntityDao();

}