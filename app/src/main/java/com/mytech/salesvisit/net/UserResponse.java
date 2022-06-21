package com.mytech.salesvisit.net;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserResponse {
    public int UserID;
    public String UserName;
    public String FullName;
    public String Designation;
    @JsonAlias({"IsAppAccessAllowed"})
    public boolean IsAppAccessAllowed;
    @JsonAlias({"IsGEOTrackEnabled"})
    public boolean IsGEOTrackEnabled;
    public String ApiAuthToken;
    public float CheckOutDistance;
    public float LogGEODistance;
    private long logGEOTime;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public boolean isAppAccessAllowed() {
        return IsAppAccessAllowed;
    }

    public void setAppAccessAllowed(boolean appAccessAllowed) {
        IsAppAccessAllowed = appAccessAllowed;
    }

    public boolean isGEOTrackEnabled() {
        return IsGEOTrackEnabled;
    }

    public void setGEOTrackEnabled(boolean GEOTrackEnabled) {
        IsGEOTrackEnabled = GEOTrackEnabled;
    }

    public String getApiAuthToken() {
        return ApiAuthToken;
    }

    public void setApiAuthToken(String apiAuthToken) {
        ApiAuthToken = apiAuthToken;
    }

    public float getCheckOutDistance() {
        return CheckOutDistance;
    }

    public void setCheckOutDistance(float checkOutDistance) {
        CheckOutDistance = checkOutDistance;
    }

    public float getLogGEODistance() {
        return LogGEODistance;
    }

    public void setLogGEODistance(float logGEODistance) {
        LogGEODistance = logGEODistance;
    }

    public long getLogGEOTime() {
        return logGEOTime;
    }

    public void setLogGEOTime(long logGEOTime) {
        this.logGEOTime = logGEOTime;
    }
}
