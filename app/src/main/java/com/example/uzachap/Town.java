package com.example.uzachap;

public class Town {
    private int townNo;
    private String townName;

    public Town(int townNo, String townName) {
        this.townNo = townNo;
        this.townName = townName;
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
}
