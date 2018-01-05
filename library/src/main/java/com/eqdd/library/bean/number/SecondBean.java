package com.eqdd.library.bean.number;

/**
 * @author吕志豪 .
 * @date 17-12-26  下午4:25.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class SecondBean<A,B> {
    A one;
    B two;

    public SecondBean(A one, B two) {
        this.one = one;
        this.two = two;
    }

    public A getOne() {
        return one;
    }

    public SecondBean setOne(A one) {
        this.one = one;
        return this;
    }

    public B getTwo() {
        return two;
    }

    public SecondBean setTwo(B two) {
        this.two = two;
        return this;
    }
}
