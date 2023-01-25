package com.mytech.salesvisit.ui.checkin;

import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.ProductModel;

import java.util.List;

public interface ResultOutput {

    public void onListResponce_Customer(List result);

    void onVersionResponse(String result);

    void onUserLocationStatus(String result);
}
