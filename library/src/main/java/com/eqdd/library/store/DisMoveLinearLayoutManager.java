package com.eqdd.library.store;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author吕志豪 .
 * @date 17-11-7  下午2:06.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class DisMoveLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public DisMoveLinearLayoutManager(Context context) {
        super(context);
    }

    public DisMoveLinearLayoutManager setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
        return this;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
