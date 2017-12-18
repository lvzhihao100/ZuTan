package com.gamerole.rxcode.demo.db;

import android.arch.lifecycle.LiveData;

import com.gamerole.rxcode.demo.db.entity.Student;

import io.reactivex.schedulers.Schedulers;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:17.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class DBUtil {
    public static void insertStudent(Student student) {
        Schedulers.io().scheduleDirect(() -> {
            DBHelper.getInstance().getDb().getStudentEntityDao().addStudent(student);
        });
    }

    public static LiveData<Student> getStudentByName(String name) {
        return DBHelper.getInstance().getDb().getStudentEntityDao().getStudentByName(name);
    }
}
