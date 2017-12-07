package com.android.orient.practice.kldf.kldf.entity.req;

public class AddBMIReq {
    private String birthdayv;
    private String genderv;
    private String heightv;
    private String mobile;
    private String password;
    private String weightv;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGenderv() {
        return this.genderv;
    }

    public void setGenderv(String genderv) {
        this.genderv = genderv;
    }

    public String getHeightv() {
        return this.heightv;
    }

    public void setHeightv(String heightv) {
        this.heightv = heightv;
    }

    public String getWeightv() {
        return this.weightv;
    }

    public void setWeightv(String weightv) {
        this.weightv = weightv;
    }

    public String getBirthdayv() {
        return this.birthdayv;
    }

    public void setBirthdayv(String birthdayv) {
        this.birthdayv = birthdayv;
    }
}
