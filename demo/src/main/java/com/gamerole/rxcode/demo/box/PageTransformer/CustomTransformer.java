package com.gamerole.rxcode.demo.box.PageTransformer;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author吕志豪 .
 * @date 17-11-29  下午4:46.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class CustomTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.4F;
    private static final float MAX_SCALE = 0.8F;
    private int deep=3;

    public CustomTransformer(int deep) {
        this.deep = deep;
    }

    @Override
    public void transformPage(View page, float position) {

        int width = page.getWidth();
        page.setTranslationX(-width * position / deep);
//        page.setRotationY(45 * position);
        if (Math.abs(position) > 1) {
            page.setZ(0);
        } else {
            page.setZ(10 - Math.abs(position * 10));
        }
    }

    public void setDeep(int deep) {
        this.deep = deep;

    }

    public int getDeep() {
        return deep;
    }
    /* public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }*/

}