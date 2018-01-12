package com.gamerole.zutan.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;


/**
 * @author吕志豪 .
 * @date 18-1-8  下午4:58.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_FRIEND_INFO_TEST)
public class FriendInfoTestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_text);

        getWindow().setEnterTransition(initContentEnterTransition());
    }





    private Transition initContentEnterTransition() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.app_slide_and_fade);


        return transition;
    }

    private void initView() {

    }




}
