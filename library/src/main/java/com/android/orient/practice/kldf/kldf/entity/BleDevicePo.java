package com.android.orient.practice.kldf.kldf.entity;

public class BleDevicePo implements Comparable<BleDevicePo> {
    private String deviceAddr;
    private String deviceName;
    private Integer distance;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddr() {
        return this.deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public Integer getDistance() {
        return this.distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public int compareTo(BleDevicePo device) {
        return device.getDistance().compareTo(getDistance());
    }
}
