package com.mytech.salesvisit.view.vieworderdetails;

import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;

public interface ViewOrderDetailsListener {
    public void onResult(String result);

    public void onResponce(OrderDetailsModel result);

}
