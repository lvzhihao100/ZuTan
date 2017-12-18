package com.gamerole.rxcode.demo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gamerole.rxcode.demo.db.entity.Student;

import java.util.List;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:11.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Dao
public interface StudentDao {
    @Query("select * FROM student")
    List<Student> getStudentList();

    @Query("select * FROM student WHERE name = :name")
    LiveData<Student> getStudentByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStudent(Student student);

    @Delete()
    void deleteStudent(Student student);
}
