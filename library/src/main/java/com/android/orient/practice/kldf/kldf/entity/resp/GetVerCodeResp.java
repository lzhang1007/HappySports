package com.android.orient.practice.kldf.kldf.entity.resp;

public class GetVerCodeResp extends BaseResp {
    private String mobile;
    private String verCode;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerCode() {
        return this.verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
