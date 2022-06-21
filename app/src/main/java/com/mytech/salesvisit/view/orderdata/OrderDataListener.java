package com.mytech.salesvisit.view.orderdata;

import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.OrderTypeModel;

import java.util.List;
public interface OrderDataListener {
    public void onResult(String result);

    public void onListResponce(OrderDataModel result);
    public void onRemoveOrder(int orderId);
    public void onEditOrder(int orderId);

    void onOrderRemoveResponce(String result);
}
