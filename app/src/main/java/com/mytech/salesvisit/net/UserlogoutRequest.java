package com.mytech.salesvisit.net;

public class UserlogoutRequest {
    private int userId;
    private GeoLocation GeoLocation;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public com.mytech.salesvisit.net.GeoLocation getGeoLocation() {
        return GeoLocation;
    }

    public void setGeoLocation(com.mytech.salesvisit.net.GeoLocation geoLocation) {
        GeoLocation = geoLocation;
    }
}
