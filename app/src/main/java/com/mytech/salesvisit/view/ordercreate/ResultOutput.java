package com.mytech.salesvisit.view.ordercreate;

import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.OrderCategoryModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.OrderTypeModel;
import com.mytech.salesvisit.model.ProductModel;
import com.mytech.salesvisit.model.UOMMOdel;

import java.util.List;

public interface ResultOutput {
    public void onResult(String result);

    public void onListResponce(List result);
    public void onListResponce_Customer(List result);
    public void onListResponce_Contact(List result);
    public void onListResponce_Address(List result);
    public void onListResponce_Employee(List result);
    public void onListResponce_OrderCategory(List result);
    public void onListResponce_Product(List<ProductModel> videos);
    public void onListResponce_UOM(List result);
    public void onResponce(OrderDetailsModel result);

    void onOrderPlaced(String body);

    void onListResponce_Dispatch(List videos);

    void onListResponce_thirdParty(List<CustomerModel> videos);
}
