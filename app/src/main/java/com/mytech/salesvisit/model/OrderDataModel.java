package com.mytech.salesvisit.model;

import java.util.List;

public class OrderDataModel {
    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<OrderData> getData() {
        return Data;
    }

    public void setData(List<OrderData> data) {
        Data = data;
    }

    int Total;
    List<OrderData> Data;
    public class OrderData {
        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int orderId) {
            OrderId = orderId;
        }

        public String getERPOrderNo() {
            return ERPOrderNo;
        }

        public void setERPOrderNo(String ERPOrderNo) {
            this.ERPOrderNo = ERPOrderNo;
        }

        public String getOrderTypeName() {
            return OrderTypeName;
        }

        public void setOrderTypeName(String orderTypeName) {
            OrderTypeName = orderTypeName;
        }

        public String getOrderDate() {
            return OrderDate;
        }

        public void setOrderDate(String orderDate) {
            OrderDate = orderDate;
        }

        public String getDeliveryDate() {
            return DeliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            DeliveryDate = deliveryDate;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getSalesPersonName() {
            return SalesPersonName;
        }

        public void setSalesPersonName(String salesPersonName) {
            SalesPersonName = salesPersonName;
        }

        public String getStatusName() {
            return StatusName;
        }

        public void setStatusName(String statusName) {
            StatusName = statusName;
        }

        public int getOrderValue() {
            return OrderValue;
        }

        public void setOrderValue(int orderValue) {
            OrderValue = orderValue;
        }

        int OrderId;
        String ERPOrderNo;
        String OrderTypeName;
        String OrderDate;
        String DeliveryDate;
        String CustomerName;
        String SalesPersonName;
        String StatusName;
        int OrderValue;
    }
}
