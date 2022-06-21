package com.mytech.salesvisit.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "userId")
    private int userID;
    @ColumnInfo(name = "username")
    private String userName;
    @ColumnInfo(name = "fullname")
    private String fullName;
    @ColumnInfo(name = "designation")
    private String designation;
    @ColumnInfo(name = "isAppAccessAllowed")
    private boolean isAppAccessAllowed;
    @ColumnInfo(name = "isGEOTrackEnabled")
    private boolean isGEOTrackEnabled;
    @ColumnInfo(name = "apiAuthToken")
    private String apiAuthToken;
    @ColumnInfo(name = "deviceId")
    private String deviceId;
    @ColumnInfo(name = "checkOutDistance")
    private float checkOutDistance;
    @ColumnInfo(name = "logGEODistance")
    private float logGEODistance;
    @ColumnInfo(name = "logGEOTime",defaultValue = "0")
    private long logGEOTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public boolean isAppAccessAllowed() {
        return isAppAccessAllowed;
    }

    public void setAppAccessAllowed(boolean appAccessAllowed) {
        isAppAccessAllowed = appAccessAllowed;
    }

    public boolean isGEOTrackEnabled() {
        return isGEOTrackEnabled;
    }

    public void setGEOTrackEnabled(boolean GEOTrackEnabled) {
        isGEOTrackEnabled = GEOTrackEnabled;
    }

    public String getApiAuthToken() {
        return apiAuthToken;
    }

    public void setApiAuthToken(String apiAuthToken) {
        this.apiAuthToken = apiAuthToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public float getCheckOutDistance() {
        return checkOutDistance;
    }

    public void setCheckOutDistance(float checkOutDistance) {
        this.checkOutDistance = checkOutDistance;
    }

    public float getLogGEODistance() {
        return logGEODistance;
    }

    public void setLogGEODistance(float logGEODistance) {
        this.logGEODistance = logGEODistance;
    }

    public long getLogGEOTime() {
        return logGEOTime;
    }

    public void setLogGEOTime(long logGEOTime) {
        this.logGEOTime = logGEOTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", designation='" + designation + '\'' +
                ", isAppAccessAllowed=" + isAppAccessAllowed +
                ", isGEOTrackEnabled=" + isGEOTrackEnabled +
                ", apiAuthToken='" + apiAuthToken + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", checkOutDistance=" + checkOutDistance +
                ", logGEODistance=" + logGEODistance +
                ", logGEOTime=" + logGEOTime +
                '}';
    }
}
