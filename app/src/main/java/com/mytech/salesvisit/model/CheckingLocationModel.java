package com.mytech.salesvisit.model;

public class CheckingLocationModel {
    public CheckingLocationModel(String allowChecking) {
        AllowChecking = allowChecking;
    }

    public String getAllowChecking() {
        return AllowChecking;
    }

    public void setAllowChecking(String allowChecking) {
        AllowChecking = allowChecking;
    }

    private String AllowChecking;

}
