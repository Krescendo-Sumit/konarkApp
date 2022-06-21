package com.mytech.salesvisit.net;

public class GeoLocation {
    private String LatLong;
    private String Accuracy;

    public GeoLocation(){
        super();
    }

    public GeoLocation(String latLong, String accuracy) {
        LatLong = latLong;
        Accuracy = accuracy;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }
}
