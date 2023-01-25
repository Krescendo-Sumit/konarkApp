package com.mytech.salesvisit.model;

public class ValidateCheckin {
    public ValidateCheckin(String checkingResponse) {
        this.checkingResponse = checkingResponse;
    }

    public String getCheckingResponse() {
        return checkingResponse;
    }

    public void setCheckingResponse(String checkingResponse) {
        this.checkingResponse = checkingResponse;
    }

    private String checkingResponse;
}
