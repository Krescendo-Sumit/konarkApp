package com.mytech.salesvisit.model;

public class ProductItems {
    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getSrNo() {
        return SrNo;
    }

    public void setSrNo(int srNo) {
        SrNo = srNo;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public float getQty() {
        return Qty;
    }

    public void setQty(float qty) {
        Qty = qty;
    }

    public int getUOMId() {
        return UOMId;
    }

    public void setUOMId(int UOMId) {
        this.UOMId = UOMId;
    }

    public String getUOMName() {
        return UOMName;
    }

    public void setUOMName(String UOMName) {
        this.UOMName = UOMName;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(float rate) {
        Rate = rate;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public boolean isRemoved() {
        return IsRemoved;
    }

    public void setRemoved(boolean removed) {
        IsRemoved = removed;
    }

    public int getDispatchItemId() {
        return DispatchItemId;
    }

    public void setDispatchItemId(int dispatchItemId) {
        DispatchItemId = dispatchItemId;
    }

    int ItemId;
    int OrderId;


    int SrNo;
    int ProductId;
    String ProductName;
    String Specification;
    float Qty;
    int UOMId;
    String UOMName;
    float Rate;
    int StatusId;
    boolean IsRemoved;
    int DispatchItemId;
}

