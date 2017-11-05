package com.example.arshiii.tutorialsgrouping;

/**
 * Created by M_Hp on 11/4/2017.
 */


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by M_Hp on 11/4/2017.
 */

public class HoldingStd  implements Serializable {
    String gidd=null;
    String gnamed=null;
    String tidd=null;
    String tnamed=null;

    ArrayList<String> studentidd=new ArrayList<>();


    public HoldingStd(String gidd, String gnamed, String tidd, String tnamed) {
        this.gidd = gidd;
        this.gnamed = gnamed;
        this.tidd = tidd;
        this.tnamed = tnamed;
    }



    public HoldingStd() {

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
