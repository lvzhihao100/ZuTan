package com.eqdd.library.bean;

/**
 * @author吕志豪 .
 * @date 18-1-23  下午4:51.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class ApplyUserBean {

    /**
     * updateTime : 2018-01-23
     * id : 1
     * applyTime : 2018-01-23
     * applyName : 吕志豪
     * zuId : 0
     * status : 申请中
     * relation : 0
     * applyPhoto : http://192.168.1.137:8073/default/1.jpg
     */

    private String updateTime;
    private int id;
    private String applyTime;
    private String applyName;
    private int zuId;
    private String status;
    private String zuName;
    private String applyPhoto;
    private int relation;


    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public int getZuId() {
        return zuId;
    }

    public void setZuId(int zuId) {
        this.zuId = zuId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZuName() {
        return zuName;
    }

    public void setZuName(String zuName) {
        this.zuName = zuName;
    }

    public String getApplyPhoto() {
        return applyPhoto;
    }

    public void setApplyPhoto(String applyPhoto) {
        this.applyPhoto = applyPhoto;
    }
}
