package com.eqdd.library.bean;

import com.eqdd.nextinputs.Scheme;

/**
 * Created by lv on 17-9-30.
 */

public class EditBean {
    String title;
    String content;
    String hint;
    String tip;
    Scheme scheme;
    int pos;

    public EditBean(String title, int pos) {
        this.title = title;
        this.pos = pos;
    }

    public String getTitle() {
        return title;
    }

    public EditBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public EditBean setContent(String content) {
        this.content = content;
        return this;
    }

    public String getHint() {
        return hint;
    }

    public EditBean setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public String getTip() {
        return tip;
    }

    public EditBean setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public EditBean setScheme(Scheme scheme) {
        this.scheme = scheme;
        return this;
    }

    public int getPos() {
        return pos;
    }

    public EditBean setPos(int pos) {
        this.pos = pos;
        return this;
    }
}
