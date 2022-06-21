package com.mytech.salesvisit.view.vieworderdetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.util.RetrofitClient;
import com.mytech.salesvisit.view.orderdata.OrderDataListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderDetailsAPI {


        Context context;
        String result = "";
        ProgressDialog progressDialog;
    ViewOrderDetailsListener resultOutput;

        public ViewOrderDetailsAPI(Context context, ViewOrderDetailsListener resultOutput) {
            this.context = context;
            this.resultOutput = resultOutput;
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait..");
        }
        public void getOrderData(int orderID)
        {
            try {

                if (!progressDialog.isShowing())
                    progressDialog.show();

                Call<OrderDetailsModel> call = RetrofitClient.getInstance().getMyApi().getViewOrderData(orderID);
                call.enqueue(new Callback<OrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                        if (response.body() != null) {
                            OrderDetailsModel result = response.body();
                            try {
                                resultOutput.onResponce(result);
                            } catch (NullPointerException e) {
                                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Log.e("Error is", t.getMessage());
                    }
                });
            } catch (Exception e) {

            }
        }



}
