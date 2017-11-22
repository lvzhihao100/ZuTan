package com.eqdd.common.bean;

import java.util.List;

/**
 * Created by lvzhihao on 17-5-11.
 */

public abstract class MultiChooseBean {
    public abstract List<MultiChooseBean> getNextDeep();
    public abstract String getContent();
}
