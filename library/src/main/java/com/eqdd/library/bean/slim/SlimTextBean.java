package com.eqdd.library.bean.slim;

/**
 * @author吕志豪 .
 * @date 17-12-26  下午3:35.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class SlimTextBean extends SlimBean{
    String content;

    public SlimTextBean(String title) {
        super(title);
    }

    public String getContent() {
        return content;
    }

    public SlimTextBean setContent(String content) {
        this.content = content;
        return this;
    }
}
