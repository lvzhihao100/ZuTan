package com.gamerole.rxcode.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.gamerole.rxcode.demo.db.DBUtil;
import com.gamerole.rxcode.demo.db.entity.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.touchView);
        viewById.setEnabled(false);
        viewById.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("MainActivity->onTouch");
                return false;
            }
        });
        DBUtil.insertStudent(new Student(12,"你好"));
        DBUtil.getStudentByName("你好").observe(this,student -> {
            System.out.println(student);
        });
    }
}
