package com.android.orient.sports.happysports.kldf.entity.resp;

public class LoginResp extends BaseResp {
    private Integer acceptMsg;
    private Integer addStepMaxNum;
    private String birthday;
    private Integer gender;
    private String handMac;
    private Integer height;
    private Integer loginNum;
    private String memberId;
    private Double stride;
    private Integer upgrade;
    private String vhstoken;
    private Double weight;

    public String getVhstoken() {
        return this.vhstoken;
    }

    public void setVhstoken(String vhstoken) {
        this.vhstoken = vhstoken;
    }

    public Integer getLoginNum() {
        return this.loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getUpgrade() {
        return this.upgrade;
    }

    public void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }

    public Integer getAcceptMsg() {
        return this.acceptMsg;
    }

    public void setAcceptMsg(Integer acceptMsg) {
        this.acceptMsg = acceptMsg;
    }

    public Integer getAddStepMaxNum() {
        return this.addStepMaxNum;
    }

    public void setAddStepMaxNum(Integer addStepMaxNum) {
        this.addStepMaxNum = addStepMaxNum;
    }

    public double getStride() {
        return this.stride;
    }

    public void setStride(Double stride) {
        this.stride = stride;
    }

    public String getHandMac() {
        return this.handMac;
    }

    public void setHandMac(String handMac) {
        this.handMac = handMac;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
