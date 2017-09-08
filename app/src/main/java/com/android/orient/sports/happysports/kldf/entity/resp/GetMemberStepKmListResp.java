package com.android.orient.sports.happysports.kldf.entity.resp;

public class GetMemberStepKmListResp extends BaseResp {
    private String allkm;
    private String allstep;
    private String ctime;

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getAllstep() {
        return this.allstep;
    }

    public void setAllstep(String allstep) {
        this.allstep = allstep;
    }

    public String getAllkm() {
        return this.allkm;
    }

    public void setAllkm(String allkm) {
        this.allkm = allkm;
    }
}
