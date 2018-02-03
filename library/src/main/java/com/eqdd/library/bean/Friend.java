package com.eqdd.library.bean;



public class Friend {

    private Long id;


    private String name;


    private String career;

    private String sex;


    private String birth;

    private String address;

    private String race;


    private String createTime;

    private String poster;

    private Long createUserId;

    private String evaluate;


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

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getBirth() {
        return birth;
    }

    public Friend setBirth(String birth) {
        this.birth = birth;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Friend setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}

