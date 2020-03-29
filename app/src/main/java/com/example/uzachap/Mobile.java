package com.example.uzachap;

public class Mobile {

    private int townNo;
    private String mobile;

    public Mobile() {
    }

    public Mobile(int townNo, String mobile) {
        this.townNo = townNo;
        this.mobile = mobile;
    }

    public int getTownNo() {
        return townNo;
    }

    public void setTownNo(int townNo) {
        this.townNo = townNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
