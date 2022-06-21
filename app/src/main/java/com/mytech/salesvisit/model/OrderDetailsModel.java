package com.mytech.salesvisit.model;

import java.util.List;

public class OrderDetailsModel {
    int OrderId;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getOrderTypeId() {
        return OrderTypeId;
    }
    public void setOrderTypeId(int orderTypeId) {
        OrderTypeId = orderTypeId;
    }

    public String getOrderTypeName() {
        return OrderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        OrderTypeName = orderTypeName;
    }

    public int getOrderCategoryId() {
        return OrderCategoryId;
    }

    public void setOrderCategoryId(int orderCategoryId) {
        OrderCategoryId = orderCategoryId;
    }

    public String getOrderCategoryName() {
        return OrderCategoryName;
    }

    public void setOrderCategoryName(String orderCategoryName) {
        OrderCategoryName = orderCategoryName;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public int getDeliveryAddressId() {
        return DeliveryAddressId;
    }

    public void setDeliveryAddressId(int deliveryAddressId) {
        DeliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryAddressName() {
        return DeliveryAddressName;
    }

    public void setDeliveryAddressName(String deliveryAddressName) {
        DeliveryAddressName = deliveryAddressName;
    }

    public int getBillingAddressId() {
        return BillingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        BillingAddressId = billingAddressId;
    }

    public String getBillingAddressName() {
        return BillingAddressName;
    }

    public void setBillingAddressName(String billingAddressName) {
        BillingAddressName = billingAddressName;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getTransportNote() {
        return TransportNote;
    }

    public void setTransportNote(String transportNote) {
        TransportNote = transportNote;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public int getCurrencyId() {
        return CurrencyId;
    }

    public void setCurrencyId(int currencyId) {
        CurrencyId = currencyId;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(String currentUser) {
        CurrentUser = currentUser;
    }

    public int getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(int salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getSalesPersonName() {
        return SalesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        SalesPersonName = salesPersonName;
    }

    public String getERPOrderNo() {
        return ERPOrderNo;
    }

    public void setERPOrderNo(String ERPOrderNo) {
        this.ERPOrderNo = ERPOrderNo;
    }

    public String getERPEntryDate() {
        return ERPEntryDate;
    }

    public void setERPEntryDate(String ERPEntryDate) {
        this.ERPEntryDate = ERPEntryDate;
    }

    public boolean isThirdParty() {
        return IsThirdParty;
    }

    public void setThirdParty(boolean thirdParty) {
        IsThirdParty = thirdParty;
    }

    public int getThirdPartyId() {
        return ThirdPartyId;
    }

    public void setThirdPartyId(int thirdPartyId) {
        ThirdPartyId = thirdPartyId;
    }

    public String getThirdPartyName() {
        return ThirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        ThirdPartyName = thirdPartyName;
    }

    public int getDispatchFrom() {
        return DispatchFrom;
    }

    public void setDispatchFrom(int dispatchFrom) {
        DispatchFrom = dispatchFrom;
    }

    public String getDispatchFromName() {
        return DispatchFromName;
    }

    public void setDispatchFromName(String dispatchFromName) {
        DispatchFromName = dispatchFromName;
    }

    public int getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(int orderBy) {
        OrderBy = orderBy;
    }

    public String getOrderByName() {
        return OrderByName;
    }

    public void setOrderByName(String orderByName) {
        OrderByName = orderByName;
    }

    public int getFollowWith() {
        return FollowWith;
    }

    public void setFollowWith(int followWith) {
        FollowWith = followWith;
    }

    public String getFollowWithName() {
        return FollowWithName;
    }

    public void setFollowWithName(String followWithName) {
        FollowWithName = followWithName;
    }

    public List<ProductItems> getProductItems() {
        return ProductItems;
    }

    public void setProductItems(List<ProductItems> productItems) {
        ProductItems = productItems;
    }

    public List<TermConditionItems> getTermConditionItems() {
        return TermConditionItems;
    }

    public void setTermConditionItems(List<TermConditionItems> termConditionItems) {
        TermConditionItems = termConditionItems;
    }

    int OrderTypeId;
    String OrderTypeName;
    int OrderCategoryId;
    String OrderCategoryName;
    int CustomerId;
    String CustomerName;
    int DeliveryAddressId;
    String DeliveryAddressName;
    int BillingAddressId;
    String BillingAddressName;
    String DeliveryDate;
    String TransportNote;
    String Remark;
    int StatusId;
    String StatusName;
    int CurrencyId;
    //             "Attachments": [
//             "50336_2.pdf"
//             ],
    boolean IsActive;
    String CurrentUser;
    int SalesPerson;
    String SalesPersonName;
    String ERPOrderNo;
    String ERPEntryDate;
    boolean IsThirdParty;
    int ThirdPartyId;
    String ThirdPartyName;
    int DispatchFrom;
    String DispatchFromName;
    int OrderBy;
    String OrderByName;
    int FollowWith;
    String FollowWithName;
    List<ProductItems> ProductItems;
    List<TermConditionItems> TermConditionItems;

    public List<String> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<String> attachments) {
        Attachments = attachments;
    }

    List<String> Attachments;

}
