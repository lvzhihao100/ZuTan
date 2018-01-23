package com.gamerole.zutan.ui.home;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.MyFragmentPagerAdapter;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.DensityUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.library.Iservice.rongtalk.RongStartService;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.User;
import com.gamerole.zutan.FriendInfoTestActivityCustom;
import com.gamerole.zutan.R;
import com.gamerole.zutan.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author吕志豪 .
 * @date 18-1-8  下午4:58.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_USER_INFO)
public class UserInfoActivity extends CommonActivity {

    private FriendInfoTestActivityCustom dataBinding;
    @Autowired
    User user;
    @Autowired
    RongStartService rongStartService;


    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_friend_info_test);

    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        initView();
        getWindow().setEnterTransition(initContentEnterTransition());
        getWindow().setSharedElementEnterTransition(initSharedElementEnterTransition());
        getWindow().setSharedElementExitTransition(null);
        getWindow().setSharedElementReturnTransition(null);
        getWindow().setReturnTransition(null);
//        getWindow().setReturnTransition(initReturnTransition());
    }

    private Transition initReturnTransition() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.app_return_slide);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
//                Animator circularReveal = ViewAnimationUtils.createCircularReveal(dataBinding.rlPhoto, dataBinding.rlPhoto.getWidth() / 2, dataBinding.rlPhoto.getHeight() / 2
//                        , Math.max(dataBinding.rlPhoto.getWidth(), dataBinding.rlPhoto.getHeight()), DensityUtil.percentW(180));
////                dataBinding.ivHead.setBackgroundColor(getResources().getColor(R.color.library_orange));
//                circularReveal.setDuration(2000);
//                circularReveal.start();
            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        return transition;
    }

    private Transition initSharedElementEnterTransition() {
        Transition sharedTransition = TransitionInflater.from(this).inflateTransition(R.transition.app_changebounds_with_arcmotion);
        sharedTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                ImageUtil.setImage(user.getPhoto(), dataBinding.ivHead);
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(dataBinding.ivHead, dataBinding.ivHead.getWidth() / 2, dataBinding.ivHead.getHeight() / 2
                        , DensityUtil.percentW(180), Math.max(dataBinding.ivHead.getWidth(), dataBinding.ivHead.getHeight()));
//                dataBinding.ivHead.setBackgroundColor(getResources().getColor(R.color.library_orange));
                circularReveal.setDuration(2000);
                circularReveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

//                        dataBinding.imageBg.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                circularReveal.start();
            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        return sharedTransition;
    }

    private Transition initContentEnterTransition() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.app_slide_and_fade);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                dataBinding.sendMsg.animate()
                        .scaleY(1)
                        .scaleX(1)
                        .start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        return transition;
    }

    private void initView() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("姓名");
        titles.add("详情");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        dataBinding.viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments));
        dataBinding.slidingTabLayout.setViewPager(dataBinding.viewPager);
        dataBinding.slidingTabLayout.getTitleView(0).setTransitionName("shared_text_");
        ClickUtil.click(dataBinding.back, () -> {
            onBackPressed();
        });
    }


    @Override
    public void setView() {
        ClickUtil.click(dataBinding.sendMsg, () -> {
            rongStartService.startPrivate(UserInfoActivity.this, user.getIdCard(), user.getName(), user.getPhoto());
        });
    }
}