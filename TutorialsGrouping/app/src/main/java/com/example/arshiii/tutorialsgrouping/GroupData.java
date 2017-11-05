package com.example.arshiii.tutorialsgrouping;

/**
 * Created by M_Hp on 11/1/2017.
 */

public class GroupData {
    private String gId;
    private String gName;
    private String gTid;
    private String gCount;

    public GroupData(String gId, String gName, String gTid, String gCount) {
        this.gId = gId;
        this.gName = gName;
        this.gTid = gTid;
        this.gCount = gCount;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgTid() {
        return gTid;
    }

    public void setgTid(String gTid) {
        this.gTid = gTid;
    }

    public String getgCount() {
        return gCount;
    }

    public void setgCount(String gCount) {
        this.gCount = gCount;
    }
}
