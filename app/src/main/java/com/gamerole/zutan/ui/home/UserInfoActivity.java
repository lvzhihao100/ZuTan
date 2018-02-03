package com.gamerole.zutan.ui.home;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.ViewAnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.adapter.ItemClickSupport;
import com.eqdd.common.adapter.MyFragmentPagerAdapter;
import com.eqdd.common.adapter.slimadapter.SlimAdapterEx;
import com.eqdd.common.adapter.slimadapter.SlimInjector;
import com.eqdd.common.adapter.slimadapter.viewinjector.IViewInjector;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.common.utils.DensityUtil;
import com.eqdd.common.utils.ImageUtil;
import com.eqdd.common.utils.PicUtil;
import com.eqdd.library.Iservice.rongtalk.RongStartService;
import com.eqdd.library.base.RoutConfig;
import com.eqdd.library.bean.room.DBUtil;
import com.eqdd.library.bean.room.User;
import com.eqdd.library.utils.HttpUtil;
import com.gamerole.zutan.FriendInfoActivityCustom;
import com.gamerole.zutan.R;
import com.gamerole.zutan.fragment.HomeFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

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

    private FriendInfoActivityCustom dataBinding;
    @Autowired
    User user;
    @Autowired
    RongStartService rongStartService;
    private MaterialDialog dialog;


    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_friend_info);
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        initView();
        getWindow().setEnterTransition(initContentEnterTransition());
        getWindow().setSharedElementEnterTransition(initSharedElementEnterTransition());
        getWindow().setSharedElementExitTransition(initSharedElementExitTransition());
        getWindow().setSharedElementReturnTransition(initSharedElementExitTransition());
        getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.app_return_slide));
    }

    private Transition initSharedElementExitTransition() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.app_clip_bounds);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(dataBinding.ivHead, dataBinding.ivHead.getWidth() / 2, dataBinding.ivHead.getHeight() / 2
                        , dataBinding.ivHead.getWidth(), DensityUtil.percentW(166));
//                dataBinding.ivHead.setBackgroundColor(getResources().getColor(R.color.library_orange));
                circularReveal.setDuration(1000);
                circularReveal.start();
                circularReveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ImageUtil.setCircleImage(user.getPhoto(), dataBinding.ivHead);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
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
        final Transition sharedTransition = TransitionInflater.from(this).inflateTransition(R.transition.app_changebounds_with_arcmotion);
        sharedTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                sharedTransition.removeListener(this);
                ImageUtil.setImage(user.getPhoto(), dataBinding.ivHead);
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(dataBinding.ivHead, dataBinding.ivHead.getWidth() / 2, dataBinding.ivHead.getHeight() / 2
                        , DensityUtil.percentW(166), Math.max(dataBinding.ivHead.getWidth(), dataBinding.ivHead.getHeight()));
                circularReveal.setDuration(2000);
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
        titles.add(user.getName());
        titles.add("详情");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        dataBinding.viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments));
        dataBinding.slidingTabLayout.setViewPager(dataBinding.viewPager);
        dataBinding.slidingTabLayout.getTitleView(0).setTransitionName("shared_text_");
        ClickUtil.click(dataBinding.back, this::onBackPressed);
    }


    @Override
    public void setView() {
        ClickUtil.click(dataBinding.sendMsg, () -> rongStartService.startPrivate(UserInfoActivity.this, user.getIdCard(), user.getName(), user.getPhoto()));
        DBUtil.getUserStatic(user1 -> {
            if (user1.getIdCard().equals(user.getIdCard())) {
                ClickUtil.click(dataBinding.ivHead, () -> {
                    if (dialog == null) {
                        dialog = getDialog();
                    }
                    dialog.show();
                });
            }
        });
    }

    private MaterialDialog getDialog() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("更改封面");
        SlimAdapterEx slimAdapterEx = SlimAdapterEx.create().registerDefault(R.layout.library_list_item_25, new SlimInjector() {
            @Override
            public void onInject(Object data, IViewInjector injector) {

                injector.text(R.id.tv_content, (String) data);
            }
        }).updateData(objects);
        MaterialDialog build = new MaterialDialog.Builder(this)
                .adapter(slimAdapterEx, new LinearLayoutManager(this))
                .build();
        ItemClickSupport.addTo(build.getRecyclerView())
                .setOnItemClickListener((recyclerView, position, v) -> {
                    if (position == 0) {
                        dialog.dismiss();
                        PicUtil.cut(UserInfoActivity.this, 630, 360);
                    }
                });
        return build;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            HttpUtil.updateUserPhoto(UserInfoActivity.this, localMedia.get(0).getCompressPath(), (status, object) -> {
                if (status == 200) {
                    User backUser = (User) object;
                    DBUtil.insertUser(backUser);
                    ImageUtil.setImage(backUser.getPhoto(), dataBinding.ivHead);
                }
            });

        }
    }
}
