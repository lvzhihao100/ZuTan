package com.eqdd.common.bean;

/**
 * Created by lvzhihao on 17-7-4.
 */

public class ThreeBean<ONE,TWO,THREE> {
    ONE one;
    TWO two;
    THREE three;

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

    public THREE getThree() {
        return three;
    }

    public void setThree(THREE three) {
        this.three = three;
    }
}
