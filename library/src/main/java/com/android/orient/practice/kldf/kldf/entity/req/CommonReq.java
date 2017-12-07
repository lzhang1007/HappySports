package com.android.orient.practice.kldf.kldf.entity.req;

public class CommonReq {
    private String acceptMsg;
    private String callNo;
    private String companyId;
    private String currentPageNum;
    private String mobile;
    private String pay;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAcceptMsg() {
        return this.acceptMsg;
    }

    public void setAcceptMsg(String acceptMsg) {
        this.acceptMsg = acceptMsg;
    }

    public String getCallNo() {
        return this.callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public String getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(String currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public String getPay() {
        return this.pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
