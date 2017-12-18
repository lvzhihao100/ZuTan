package com.gamerole.rxcode.demo.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.gamerole.rxcode.demo.db.entity.Student;

/**
 * @author吕志豪 .
 * @date 17-11-29  上午9:58.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class StudentViewModel extends ViewModel {


    private MutableLiveData<Student> user;

    public LiveData<Student> getUser() {
        if (user == null)
            user = new MutableLiveData<>();
        return user;
    }

    public void setUsername(String username) {
        user.setValue(new Student(1, username));
    }

}