package com.gamerole.rxcode.demo.box.PageTransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author吕志豪 .
 * @date 17-11-29  下午4:46.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class MapTransformer implements ViewPager.PageTransformer {
    //卡片之间的高度差
    private static final float LENTH = 100;

    @Override
    public void transformPage(View page, float position) {
        page.setTranslationY(LENTH * Math.abs(position));
    }
}