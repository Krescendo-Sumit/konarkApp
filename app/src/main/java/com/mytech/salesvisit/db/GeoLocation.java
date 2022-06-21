package com.mytech.salesvisit.db;

import androidx.room.ColumnInfo;

public class GeoLocation {
    private String accuracy;
    private String latlng;
    @ColumnInfo(name = "geotype")
    private GeoType type;

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public GeoType getType() {
        return type;
    }

    public void setType(GeoType type) {
        this.type = type;
    }
}
