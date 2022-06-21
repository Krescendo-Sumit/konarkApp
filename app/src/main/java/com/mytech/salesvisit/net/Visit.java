package com.mytech.salesvisit.net;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Visit {
    private int Id;
    private int UserId;
    @JsonAlias({"FK_CustomerId"})
    private int CustomerId;
    private String CheckIn;
    private String CheckOut;
    private boolean IsDraftVisit;
    private String VisitId;
    private boolean IsActive;
    private String CustomerName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(String checkIn) {
        CheckIn = checkIn;
    }

    public String getCheckOut() {
        return CheckOut;
    }

    public void setCheckOut(String checkOut) {
        CheckOut = checkOut;
    }

    public boolean isIsDraftVisit() {
        return IsDraftVisit;
    }

    public void setIsDraftVisit(boolean draftVisit) {
        IsDraftVisit = draftVisit;
    }

    public String getVisitId() {
        return VisitId;
    }

    public void setVisitId(String visitId) {
        VisitId = visitId;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean active) {
        IsActive = active;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
}
