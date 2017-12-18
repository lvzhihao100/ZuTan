package com.gamerole.rxcode.demo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 17-11-28  下午4:09.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Entity(tableName = "student")
public class Student implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Student() {
    }

    public Student(int i, String username) {
        this.id=i;
        this.name=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
