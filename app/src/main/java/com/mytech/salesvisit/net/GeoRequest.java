package com.mytech.salesvisit.net;

public class GeoRequest {

    private Integer UserId;
    private Integer GEOLogTypeId;
    private GeoLocation GEOLocation;
    private String LogDateTime;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getGEOLogTypeId() {
        return GEOLogTypeId;
    }

    public void setGEOLogTypeId(Integer GEOLogTypeId) {
        this.GEOLogTypeId = GEOLogTypeId;
    }

    public GeoLocation getGEOLocation() {
        return GEOLocation;
    }

    public void setGEOLocation(GeoLocation GEOLocation) {
        this.GEOLocation = GEOLocation;
    }

    public String getLogDateTime() {
        return LogDateTime;
    }

    public void setLogDateTime(String logDateTime) {
        LogDateTime = logDateTime;
    }
}
