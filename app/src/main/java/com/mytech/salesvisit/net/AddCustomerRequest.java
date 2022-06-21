package com.mytech.salesvisit.net;

public class AddCustomerRequest {
    private String CustomerName;
    private int StateId;
    private int CityId;
    private int UserId;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
