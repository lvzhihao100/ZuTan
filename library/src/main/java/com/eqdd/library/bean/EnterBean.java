package com.eqdd.library.bean;

/**
 * Created by lv on 17-9-29.
 */

public class EnterBean {
    String content;
    String enterActivity;

    public String getEnterActivity() {
        return enterActivity;
    }

    public EnterBean setEnterActivity(String enterActivity) {
        this.enterActivity = enterActivity;
        return this;
    }


    public EnterBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public EnterBean setContent(String content) {
        this.content = content;
        return this;
    }
}
