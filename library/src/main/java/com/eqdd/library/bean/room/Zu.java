package com.eqdd.library.bean.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 18-1-18  下午1:54.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Entity
public class Zu implements Serializable{


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
    @PrimaryKey
    private Long id;
    private String name;
    private String watchword;
    private String logo;
    private String createTime;
    private String poster;
    private long createUserId;
    private long holderUserId;
    private String holderUserIdCard;

    public String getHolderUserIdCard() {
        return holderUserIdCard;
    }

    public void setHolderUserIdCard(String holderUserIdCard) {
        this.holderUserIdCard = holderUserIdCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWatchword() {
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

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public long getHolderUserId() {
        return holderUserId;
    }


    public void setHolderUserId(long holderUserId) {
        this.holderUserId = holderUserId;
    }
}
