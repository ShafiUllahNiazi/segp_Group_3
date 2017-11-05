package com.example.arshiii.tutorialsgrouping;

/**
 * Created by Arshiii on 10/24/2017.
 */

public class Student {
    private String uob;
    private String stdName;
    private String stdYear;
    private String groupName;
    private Boolean checkBox;

    public Student() {
        uob = null;
        stdName = null;
        stdYear = null;
        groupName = null;
        checkBox = false;
    }

    public Student(String uob, String stdName, String stdYear,String groupName) {
        this.uob = uob;
        this.stdName = stdName;
        this.stdYear = stdYear;
        this.groupName = groupName;
        checkBox = false;
    }

    public Boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(Boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUob() {
        return uob;
    }

    public void setUob(String uob) {
        this.uob = uob;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdYear() {
        return stdYear;
    }

    public void setStdYear(String stdYear) {
        this.stdYear = stdYear;
    }

}
