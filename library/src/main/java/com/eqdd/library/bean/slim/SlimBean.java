package com.eqdd.library.bean.slim;

/**
 * @author吕志豪 .
 * @date 17-12-26  下午3:34.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class SlimBean {
    String title;
    int pos;

    public SlimBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public SlimBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getPos() {
        return pos;
    }

    public SlimBean setPos(int pos) {
        this.pos = pos;
        return this;
    }
}
