package com.android.orient.practice.kldf.kldf.entity.req;

public class UpPasswordCodeReq {
    private String mobile;
    private String newPassword;
    private String verCode;

    public String getVerCode() {
        return this.verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
