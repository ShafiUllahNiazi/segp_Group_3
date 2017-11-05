package com.example.arshiii.tutorialsgrouping;

/**
 * Created by Arshiii on 10/28/2017.
 */

public class Teacher {
    private String mTeacherName;
    private String mTeacherEmail;
    private String mGroupName;
    private String keyId;

    public Teacher() {
         mTeacherName=null;
        mTeacherEmail = null;
        mGroupName = null;
        keyId = null;
    }

    public Teacher(String keyId,String mTeacherEmail,String mTeacherName,String grpName) {
        this.mTeacherName = mTeacherName;
        this.mTeacherEmail = mTeacherEmail;
        this.mGroupName = grpName;
        this.keyId = keyId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getmGroupName() {
        return mGroupName;
    }

    public void setmGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public String getmTeacherName() {
        return mTeacherName;
    }

    public void setmTeacherName(String mTeacherName) {
        this.mTeacherName = mTeacherName;
    }

    public String getmTeacherEmail() {
        return mTeacherEmail;
    }

    public void setmTeacherEmail(String mTeacherEmail) {
        this.mTeacherEmail = mTeacherEmail;
    }
}
