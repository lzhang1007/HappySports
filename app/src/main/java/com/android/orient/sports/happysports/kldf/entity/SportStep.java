package com.android.orient.sports.happysports.kldf.entity;

public class SportStep {
    private Integer calorie;
    private String ctime;
    private Integer deviceType;
    private Float distance;
    private String mac;
    private String memberId;
    private Integer seconds;
    private String sportDate;
    private String sportStepId;
    private Integer sportType;
    private Integer step;
    private Integer uploadCount;
    private Integer uploadFlag;
    private String uploadTime;
    private String utime;

    public String getSportStepId() {
        return this.sportStepId;
    }

    public void setSportStepId(String sportStepId) {
        this.sportStepId = sportStepId;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getSportType() {
        return this.sportType;
    }

    public void setSportType(Integer sportType) {
        this.sportType = sportType;
    }

    public Integer getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getStep() {
        return this.step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getCalorie() {
        return this.calorie;
    }

    public void setCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    public Float getDistance() {
        return this.distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getSeconds() {
        return this.seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public String getSportDate() {
        return this.sportDate;
    }

    public void setSportDate(String sportDate) {
        this.sportDate = sportDate;
    }

    public Integer getUploadFlag() {
        return this.uploadFlag;
    }

    public void setUploadFlag(Integer uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getUploadTime() {
        return this.uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getUploadCount() {
        return this.uploadCount;
    }

    public void setUploadCount(Integer uploadCount) {
        this.uploadCount = uploadCount;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return this.utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}
