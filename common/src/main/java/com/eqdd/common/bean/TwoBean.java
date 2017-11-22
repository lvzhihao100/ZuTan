package com.eqdd.common.bean;

/**
 * Created by lvzhihao on 17-7-4.
 */

public class TwoBean<ONE, TWO> {
    ONE one;
    TWO two;

    public TwoBean(ONE one, TWO two) {
        this.one = one;
        this.two = two;
    }

    public ONE getOne() {
        return one;
    }

    public void setOne(ONE one) {
        this.one = one;
    }

    public TWO getTwo() {
        return two;
    }

    public void setTwo(TWO two) {
        this.two = two;
    }
}
