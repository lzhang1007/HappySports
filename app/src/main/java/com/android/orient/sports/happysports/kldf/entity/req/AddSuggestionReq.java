package com.android.orient.sports.happysports.kldf.entity.req;

public class AddSuggestionReq {
    private String mobile;
    private String suggestion;

    public String getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
