package com.mytech.salesvisit.view.orderdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDataAPI {


        Context context;
        String result = "";
        ProgressDialog progressDialog;
        OrderDataListener resultOutput;

        public OrderDataAPI(Context context, OrderDataListener resultOutput) {
            this.context = context;
            this.resultOutput = resultOutput;
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait..");
        }
        public void getOrderData(JsonObject jsonObject)
        {
            try {

                if (!progressDialog.isShowing())
                    progressDialog.show();

                Call<OrderDataModel> call = RetrofitClient.getInstance().getMyApi().getOrderDcata(jsonObject);
                call.enqueue(new Callback<OrderDataModel>() {
                    @Override
                    public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                        if (response.body() != null) {
                            OrderDataModel result = response.body();
                            try {
                                resultOutput.onListResponce(result);
                            } catch (NullPointerException e) {
                                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }else
                        {

                            Toast.makeText(context, "No More Records.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDataModel> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Log.e("Error is", t.getMessage());
                    }
                });
            } catch (Exception e) {

            }
        }

    public void removeOrder(int orderId)
    {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<String> call = RetrofitClient.getInstance().getMyApi().removeOrder(orderId);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        String result = response.body();

                        try {

                            resultOutput.onOrderRemoveResponce(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                    resultOutput.onOrderRemoveResponce(t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }




}
