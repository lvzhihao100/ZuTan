package com.eqdd.library.bean.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author吕志豪 .
 * @date 17-10-18  上午11:49.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
@Entity(indices = {@Index(value = "idCard", unique = true)})
public class User implements Serializable{

    /**
     * address : string
     * birth : string
     * faceToken : string
     * facesetToken : string
     * id : 0
     * idCard : string
     * name : string
     * password : string
     * race : string
     * sex : string
     */
    @PrimaryKey
    private int id;
    private String address;
    private String birth;
    private String faceToken;
    private String facesetToken;
    private String idCard;
    private String name;
    private String password;
    private String race;
    private String sex;
    private String token;
    private String photo;
    private int zuId;

    public int getZuId() {
        return zuId;
    }

    public void setZuId(int zuId) {
        this.zuId = zuId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public String getFacesetToken() {
        return facesetToken;
    }

    public void setFacesetToken(String facesetToken) {
        this.facesetToken = facesetToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", faceToken='" + faceToken + '\'' +
                ", facesetToken='" + facesetToken + '\'' +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", race='" + race + '\'' +
                ", sex='" + sex + '\'' +
                ", token='" + token + '\'' +
                ", photo='" + photo + '\'' +
                ", zuId=" + zuId +
                '}';
    }
}
