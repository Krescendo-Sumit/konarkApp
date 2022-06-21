package com.mytech.salesvisit.net;

public class UserCheckRequest {
    private Integer UserId;
    private String CustomerId;
    private GeoLocation GeoLocation;

    public com.mytech.salesvisit.net.GeoLocation getGeoLocation() {
        return GeoLocation;
    }

    public void setGeoLocation(com.mytech.salesvisit.net.GeoLocation geoLocation) {
        GeoLocation = geoLocation;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }
}
