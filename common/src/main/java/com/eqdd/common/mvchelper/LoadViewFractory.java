package com.eqdd.common.mvchelper;

import com.shizhefei.mvc.ILoadViewFactory;

/**
 * Created by vzhihao on 2016/11/24.
 */
public class LoadViewFractory implements ILoadViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return null;
    }

    @Override
    public ILoadView madeLoadView() {
        return null;
    }
}
