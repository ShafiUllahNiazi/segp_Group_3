package com.example.arshiii.tutorialsgrouping;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by M_Hp on 11/4/2017.
 */

public class Holding  implements Serializable{
    String gidd=null;
    String gnamed=null;
    String tidd=null;
    String tnamed=null;
    String newTid=null;
    String newTechName=null;
   ArrayList<String> studentidd=new ArrayList<>();


    public Holding(String gidd, String gnamed, String tidd, String tnamed) {
        this.gidd = gidd;
        this.gnamed = gnamed;
        this.tidd = tidd;
        this.tnamed = tnamed;
    }

    public String getNewTid() {
        return newTid;
    }

    public void setNewTid(String newTid) {
        this.newTid = newTid;
    }

    public String getNewTechName() {
        return newTechName;
    }

    public void setNewTechName(String newTechName) {
        this.newTechName = newTechName;
    }

    public Holding() {

    }

    public String getGidd() {
        return gidd;
    }

    public void setGidd(String gidd) {
        this.gidd = gidd;
    }

    public String getGnamed() {
        return gnamed;
    }

    public void setGnamed(String gnamed) {
        this.gnamed = gnamed;
    }

    public String getTidd() {
        return tidd;
    }

    public void setTidd(String tidd) {
        this.tidd = tidd;
    }

    public String getTnamed() {
        return tnamed;
    }

    public void setTnamed(String tnamed) {
        this.tnamed = tnamed;
    }
}
