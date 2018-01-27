package com.gamerole.zutan.bean;

/**
 * @author吕志豪 .
 * @date 17-12-22  下午2:27.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class UserLocationBean {


    /**
     * catongImg :
     * address : 2
     * locationId : 2
     * latitude : 2
     * name : 胡勇
     * updateTime : 1513151843000
     * userId : 2
     * longitude : 2
     */

    private String catongImg;
    private String address;
    private long locationId;
    private double latitude;
    private String name;
    private String updateTime;
    private long userId;
    private double longitude;

    public String getCatongImg() {
        return catongImg;
    }

    public void setCatongImg(String catongImg) {
        this.catongImg = catongImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
