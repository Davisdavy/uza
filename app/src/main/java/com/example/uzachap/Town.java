package com.example.uzachap;

public class Town {
    private String mobile;
    private String town_name;

    public Town(String mobile, String town_name) {
        this.mobile = mobile;
        this.town_name = town_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }
}
