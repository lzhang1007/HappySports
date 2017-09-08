package com.android.orient.sports.happysports.kldf.entity.resp;

public class DynamicResp {
    private String dynamicZyText;
    private String hrefUrl;
    private Integer imgType;
    private String pubTime;
    private String title;
    private String urls;

    public String getUrls() {
        return this.urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getPubTime() {
        return this.pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getDynamicZyText() {
        return this.dynamicZyText;
    }

    public void setDynamicZyText(String dynamicZyText) {
        this.dynamicZyText = dynamicZyText;
    }

    public String getHrefUrl() {
        return this.hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImgType() {
        return this.imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }
}
