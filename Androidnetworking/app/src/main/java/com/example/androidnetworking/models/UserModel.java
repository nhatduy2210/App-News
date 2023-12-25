package com.example.androidnetworking.models;

public class UserModel {
    private Integer ID;
    private String EMAIL,PASSWORD,NAME,ROLE,AVATAR;

    public UserModel(Integer ID, String EMAIL, String PASSWORD, String NAME, String ROLE, String AVATAR) {
        this.ID = ID;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.NAME = NAME;
        this.ROLE = ROLE;
        this.AVATAR = AVATAR;
    }

    public UserModel() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public String getAVATAR() {
        return AVATAR;
    }

    public void setAVATAR(String AVATAR) {
        this.AVATAR = AVATAR;
    }
}




