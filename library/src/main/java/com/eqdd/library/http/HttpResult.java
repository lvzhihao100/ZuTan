package com.eqdd.library.http;

import java.io.Serializable;

/**
 * Created by lvzhihao on 17-7-4.
 */

public class HttpResult<T> implements Serializable {
    String msg;
    int status;
    T items;

    @Override
    public String toString() {
        return "HttpResult{" +
                "msg='" + msg + '\'' +
                ", status=" + status +
                ", items=" + items +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }
}
