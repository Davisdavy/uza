package com.example.alveen;

public class Town {
    private int townNo;
    private String townName;
    private String mobileNo;

    public Town() {
    }

    public Town(int townNo, String townName, String mobileNo) {
        this.townNo = townNo;
        this.townName = townName;
        this.mobileNo = mobileNo;
    }

    public int getTownNo() {
        return townNo;
    }

    public void setTownNo(int townNo) {
        this.townNo = townNo;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
