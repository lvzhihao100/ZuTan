package com.eqdd.library.bean;

import java.io.Serializable;

/**
 * Created by lv on 17-9-29.
 */

public class QiyeImgBean implements Serializable {

    /**
     * Id : 3
     * creater : 966dc074e3f14695a32b8932e0ad5490
     * createTime : 2017-09-22T10:12:04.93
     * updater : null
     * updateTime : null
     * isDel : false
     * imageUrl : http://47.94.173.253:8008/image/20170922/15690783806/17092210120482712.png;
     * Url : www.baidu.com
     * title : 百度
     * sort : 1
     */

    private int Id;
    private String creater;
    private String createTime;
    private String updater;
    private String updateTime;
    private boolean isDel;
    private String imageUrl;
    private String Url;
    private String title;
    private int sort;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
