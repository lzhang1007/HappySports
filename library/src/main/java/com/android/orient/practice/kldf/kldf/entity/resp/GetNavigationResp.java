package com.android.orient.practice.kldf.kldf.entity.resp;

public class GetNavigationResp {
    private String footerName;
    private Integer nindex;
    private Integer topType;
    private String topUrl;

    public String getTopUrl() {
        return this.topUrl;
    }

    public void setTopUrl(String topUrl) {
        this.topUrl = topUrl;
    }

    public String getFooterName() {
        return this.footerName;
    }

    public void setFooterName(String footerName) {
        this.footerName = footerName;
    }

    public Integer getTopType() {
        return this.topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public Integer getNindex() {
        return this.nindex;
    }

    public void setNindex(Integer nindex) {
        this.nindex = nindex;
    }
}
