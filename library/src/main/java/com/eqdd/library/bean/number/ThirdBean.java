package com.eqdd.library.bean.number;

/**
 * @author吕志豪 .
 * @date 17-12-26  下午4:25.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class ThirdBean<A, B, C> extends SecondBean<A, B> {

    C three;

    public ThirdBean(A one, B two, C three) {
        super(one, two);
        this.one = one;
        this.two = two;
        this.three = three;
    }


    public C getThree() {
        return three;
    }

    public ThirdBean setThree(C three) {
        this.three = three;
        return this;
    }
}
