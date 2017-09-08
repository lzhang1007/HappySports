package com.android.orient.sports.happysports.kldf.entity.resp;

public class ScoreDetailResp {
    private String actionName;
    private String ctime;
    private String gold;
    private String score;

    public String getGold() {
        return this.gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
