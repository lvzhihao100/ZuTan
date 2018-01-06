package com.gamerole.zutan.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.transition.TransitionManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.eqdd.common.ActivityFrameLayoutCustom;
import com.eqdd.common.base.CommonActivity;
import com.eqdd.common.utils.ClickUtil;
import com.eqdd.library.base.RoutConfig;
import com.gamerole.zutan.R;
import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

/**
 * @author吕志豪 .
 * @date 18-1-5  下午5:14.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Route(path = RoutConfig.APP_GUIDE)
public class GuideActivity extends CommonActivity {

    private ActivityFrameLayoutCustom dataBinding;

    @Override
    public void initBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.common_activity_framelayout);
    }

    @Override
    public void initData() {

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnboarding(), getApplicationContext());

        engine.setOnChangeListener((oldElementIndex, newElementIndex) -> {
            if (newElementIndex == 2) {
                TransitionManager.beginDelayedTransition(dataBinding.onboardingRootView);
                dataBinding.btEnter.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(dataBinding.onboardingRootView);
                dataBinding.btEnter.setVisibility(View.GONE);
            }
        });
        ClickUtil.click(dataBinding.btEnter, () -> {
            ARouter.getInstance().build(RoutConfig.APP_LOGIN).navigation();
        });
    }

    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Hotels", "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#678FB4"), R.mipmap.app_ic_home, R.mipmap.app_ic_home);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Banks", "We carefully verify all banks before add them into the app",
                Color.parseColor("#65B0B4"), R.mipmap.app_ic_home, R.mipmap.app_ic_home);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Stores", "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.mipmap.app_ic_home, R.mipmap.app_ic_home);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }

    @Override
    public void setView() {

    }
}
