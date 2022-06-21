package com.mytech.salesvisit.net;

public class UserRequest {
    private String UserName;
    private String Password;
    private String DeviceId;
    private String DeviceName;
    private GeoLocation GeoLocation;

    public GeoLocation getGeoLocation() {
        return GeoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        GeoLocation = geoLocation;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }
}
