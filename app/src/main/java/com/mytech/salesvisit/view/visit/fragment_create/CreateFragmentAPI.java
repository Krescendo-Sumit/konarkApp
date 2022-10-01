
package com.mytech.salesvisit.view.visit.fragment_create;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.model.ContactModel;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.EmployeeModel;
import com.mytech.salesvisit.model.MOMPerticularModel;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.RemarkAboutModel;
import com.mytech.salesvisit.model.RemarkDetailsModel;
import com.mytech.salesvisit.model.VisitModel;
import com.mytech.salesvisit.util.RetrofitClient;
import com.mytech.salesvisit.view.orderdata.OrderDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragmentAPI {
    Context context;
    String result = "";
    ProgressDialog progressDialog;
    CreateFragmentListener resultOutput;

    public CreateFragmentAPI(Context context, CreateFragmentListener resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
    }
    public void getCompanionPeople()
    {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<EmployeeModel>> call = RetrofitClient.getInstance().getMyApi().getCompanionPeople();
            call.enqueue(new Callback<List<EmployeeModel>>() {
                @Override
                public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if (response.body() != null) {
                        List<EmployeeModel> result = response.body();
                        try {
                            resultOutput.onCompanionPeopleResult(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 123 " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {

                        Toast.makeText(context, "No More Records.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }
    public void getPersonResponsible()
    {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<EmployeeModel>> call = RetrofitClient.getInstance().getMyApi().getCompanionPeople();
            call.enqueue(new Callback<List<EmployeeModel>>() {
                @Override
                public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if (response.body() != null) {
                        List<EmployeeModel> result = response.body();
                        try {
                            resultOutput.onPersonResponsibleResult(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 123 " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {

                        Toast.makeText(context, "No More Records.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getCustomer(JsonObject jsonObject) {
        try {

          /*  if (!progressDialog.isShowing())
                progressDialog.show();*/

            Call<List<CustomerModel>> call = RetrofitClient.getInstance().getMyApi().getCustomer(jsonObject);
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

    void getPersonVisited(int code) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<ContactModel>> call = RetrofitClient.getInstance().getMyApi().getGivenBY(code);
            call.enqueue(new Callback<List<ContactModel>>() {
                @Override
                public void onResponse(Call<List<ContactModel>> call, Response<List<ContactModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<ContactModel> videos = response.body();
                        try {

                            resultOutput.onGetPersonVisited(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ContactModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }
    void getVisitAttendee(int code) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<ContactModel>> call = RetrofitClient.getInstance().getMyApi().getGivenBY(code);
            call.enqueue(new Callback<List<ContactModel>>() {
                @Override
                public void onResponse(Call<List<ContactModel>> call, Response<List<ContactModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<ContactModel> videos = response.body();
                        try {

                            resultOutput.onGetVisitAttendee(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ContactModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }

    void getVisitReason() {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<VisitModel>> call = RetrofitClient.getInstance().getMyApi().getVisitReason();
            call.enqueue(new Callback<List<VisitModel>>() {
                @Override
                public void onResponse(Call<List<VisitModel>> call, Response<List<VisitModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<VisitModel> videos = response.body();
                        try {

                            resultOutput.onVisitReason(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<VisitModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }

    void getRemarkAbout(String value) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<RemarkAboutModel>> call = RetrofitClient.getInstance().getMyApi().getRemarkAbout(value);
            call.enqueue(new Callback<List<RemarkAboutModel>>() {
                @Override
                public void onResponse(Call<List<RemarkAboutModel>> call, Response<List<RemarkAboutModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<RemarkAboutModel> videos = response.body();
                        try {

                            resultOutput.onRemarkAbout(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RemarkAboutModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }
    void getRemarkDetails(int value) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<RemarkDetailsModel>> call = RetrofitClient.getInstance().getMyApi().getRemarkDetails(value);
            call.enqueue(new Callback<List<RemarkDetailsModel>>() {
                @Override
                public void onResponse(Call<List<RemarkDetailsModel>> call, Response<List<RemarkDetailsModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<RemarkDetailsModel> videos = response.body();
                        try {

                            resultOutput.onRemarkDetails(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RemarkDetailsModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }
    void getMOMPerticular() {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<MOMPerticularModel>> call = RetrofitClient.getInstance().getMyApi().getMOMPerticular();
            call.enqueue(new Callback<List<MOMPerticularModel>>() {
                @Override
                public void onResponse(Call<List<MOMPerticularModel>> call, Response<List<MOMPerticularModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<MOMPerticularModel> videos = response.body();
                        try {

                            resultOutput.onMOMPerticularResult(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MOMPerticularModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }



   /* public void removeOrder(int orderId)
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
*/



}
