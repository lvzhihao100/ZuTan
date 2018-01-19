package com.eqdd.library.bean;

/**
 * @author吕志豪 .
 * @date 18-1-18  下午1:54.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class Zu {


    /**
     * id : 5
     * name : root
     * watchword : null
     * logo : http://192.168.1.137:8073/zu/63a9f1516240598005.jpg
     * createTime : 2018-01-18
     * poster : null
     * createUserId : 3
     * holderUserId : 3
     */

    private int id;
    private String name;
    private String watchword;
    private String logo;
    private String createTime;
    private String poster;
    private int createUserId;
    private int holderUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getWatchword() {
        return watchword;
    }

    public void setWatchword(String watchword) {
        this.watchword = watchword;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getHolderUserId() {
        return holderUserId;
    }

    public void setHolderUserId(int holderUserId) {
        this.holderUserId = holderUserId;
    }
}
