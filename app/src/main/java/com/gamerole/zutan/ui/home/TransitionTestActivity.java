package com.gamerole.zutan.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gamerole.zutan.R;

/**
 * @author吕志豪 .
 * @date 18-1-10  上午9:04.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class TransitionTestActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_transition_test);
        view = findViewById(R.id.tv_content);

    }

    public void trans(View view) {
        Intent intent = new Intent(this, FriendInfoActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "content");
        startActivity(intent, activityOptionsCompat.toBundle());
    }
}
