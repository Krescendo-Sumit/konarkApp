
package com.mytech.salesvisit.ui.checkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mytech.salesvisit.BuildConfig;
import com.mytech.salesvisit.model.AddressModel;
import com.mytech.salesvisit.model.ContactModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.OrderCategoryModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.OrderTypeModel;
import com.mytech.salesvisit.model.ProductModel;
import com.mytech.salesvisit.model.UOMMOdel;
import com.mytech.salesvisit.util.RetrofitClient;
import com.mytech.salesvisit.util.VectorTOJsonConverter;


import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckinAPI {

    Context context;
    String result = "";
    ProgressDialog progressDialog;
   ResultOutput resultOutput;

    public CheckinAPI(Context context, ResultOutput resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
    }


    public void getCustomer(JsonObject jsonObject) {
        try {

          /*  if (!progressDialog.isShowing())
                progressDialog.show();*/

            Call<List<CustomerModel>> call = RetrofitClient.getInstance().getMyApi().getCustomerCheckIn(jsonObject);
            call.enqueue(new Callback<List<CustomerModel>>() {
                @Override
                public void onResponse(Call<List<CustomerModel>> call, Response<List<CustomerModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<CustomerModel> videos = response.body();
                        try {
                            resultOutput.onListResponce_Customer(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
Log.i("Error is",e.getMessage());
        }
    }

    public void getVersionCode() {
        try {

          /*  if (!progressDialog.isShowing())
                progressDialog.show();*/

            Call<String> call = RetrofitClient.getInstance().getMyApi().getVersionCode();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        String videos = response.body();
                        try {
                            resultOutput.onVersionResponse(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }

}
