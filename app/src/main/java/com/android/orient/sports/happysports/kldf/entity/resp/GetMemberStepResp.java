package com.android.orient.sports.happysports.kldf.entity.resp;

public class GetMemberStepResp extends BaseResp {
    private String handMac;
    private Double km;
    private Integer step;

    public Integer getStep() {
        return this.step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Double getKm() {
        return this.km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getHandMac() {
        return this.handMac;
    }

    public void setHandMac(String handMac) {
        this.handMac = handMac;
    }
}
