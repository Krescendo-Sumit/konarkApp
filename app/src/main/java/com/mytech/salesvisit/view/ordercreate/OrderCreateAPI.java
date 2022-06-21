
package com.mytech.salesvisit.view.ordercreate;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
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
import com.mytech.salesvisit.util.SqlightDatabaseUtil;
import com.mytech.salesvisit.util.VectorTOJsonConverter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCreateAPI {

    Context context;
    String result = "";
    ProgressDialog progressDialog;
    ResultOutput resultOutput;

    public OrderCreateAPI(Context context, ResultOutput resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
    }

    public void getOrderType() {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<OrderTypeModel>> call = RetrofitClient.getInstance().getMyApi().getOrderType();
            call.enqueue(new Callback<List<OrderTypeModel>>() {
                @Override
                public void onResponse(Call<List<OrderTypeModel>> call, Response<List<OrderTypeModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<OrderTypeModel> videos = response.body();
                        try {
                            resultOutput.onListResponce(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<OrderTypeModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }
    String[] getTitleFromList(List<OrderTypeModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }
    String[] getTitleFromList_For_Customer(List<CustomerModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }
    String[] getTitleFromList_For_Contact(List<ContactModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
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

    void getGivenBy(int code) {
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
                            resultOutput.onListResponce_Contact(videos);
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

    public void getAddress(int code) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<AddressModel>> call = RetrofitClient.getInstance().getMyApi().getAddress(code);
            call.enqueue(new Callback<List<AddressModel>>() {
                @Override
                public void onResponse(Call<List<AddressModel>> call, Response<List<AddressModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<AddressModel> videos = response.body();
                        try {
                            resultOutput.onListResponce_Address(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AddressModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }

    String[] getTitleFromList_For_Address(List<AddressModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }

    public void getSalesPerson() {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<EmployeeModel>> call = RetrofitClient.getInstance().getMyApi().getSalesPerson();
            call.enqueue(new Callback<List<EmployeeModel>>() {
                @Override
                public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<EmployeeModel> videos = response.body();
                        try {
                            resultOutput.onListResponce_Employee(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
            Log.i("Error is",e.getMessage());
        }
    }

    String[] getTitleFromList_For_Employee(List<EmployeeModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }


    public void getOrderCategory() {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<OrderCategoryModel>> call = RetrofitClient.getInstance().getMyApi().getOrderCategory();
            call.enqueue(new Callback<List<OrderCategoryModel>>() {
                @Override
                public void onResponse(Call<List<OrderCategoryModel>> call, Response<List<OrderCategoryModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<OrderCategoryModel> videos = response.body();
                        try {
                            resultOutput.onListResponce_OrderCategory(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<OrderCategoryModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }

    String[] getTitleFromList_For_OrderCategory(List<OrderCategoryModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];
                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }

    public void getProducts(JsonObject jsonObject) {
        try {

        /*    if (!progressDialog.isShowing())
                progressDialog.show();*/

            Call<List<ProductModel>> call = RetrofitClient.getInstance().getMyApi().getProductList(jsonObject);
            call.enqueue(new Callback<List<ProductModel>>() {
                @Override
                public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<ProductModel> videos = response.body();
                        try {
                            resultOutput.onListResponce_Product(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }

    String[] getTitleFromList_For_Product(List<ProductModel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()+1];
                data[0]="Select";
                for(int i=1;i<list.size()+1;i++)
                {
                    data[i]=list.get(i-1).getText().toString().trim();
                }
            }
            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }

    public void getUOM() {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<UOMMOdel>> call = RetrofitClient.getInstance().getMyApi().getUOM();
            call.enqueue(new Callback<List<UOMMOdel>>() {
                @Override
                public void onResponse(Call<List<UOMMOdel>> call, Response<List<UOMMOdel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<UOMMOdel> videos = response.body();
                        try {
                            resultOutput.onListResponce_UOM(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is 1 " + e.getMessage()+videos.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is 2" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<UOMMOdel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i("Error is",e.getMessage());
        }
    }
    String[] getTitleFromList_For_UOM(List<UOMMOdel> list)
    {
        String data[]=null;
        try{
            if(list!=null && list.size()>0)
            {
                data=new String[list.size()];

                for(int i=0;i<list.size();i++)
                {
                    data[i]=list.get(i).getText().toString().trim();
                }
            }

            return data;
        }catch (Exception e)
        {
            return  null;
        }
    }

    public void addOrder(String str_ordertypeid, String str_customerid, String str_givenbyid, String str_followupid, String str_deliveryaddressid, String str_billingaddressid, String str_isthirdpartyid, String str_deliverydate, String str_transportnote, String str_dispatchfrom, String str_saleperson, String str_remark, String str_ordercategoryid, Vector[] items, Vector[] terms,boolean isThirdPartyChecked,String attachemnt,JsonObject jsonObject_local) {
        try {
            JsonArray jsonArray = new VectorTOJsonConverter().getJsonArray(items);
            Toast.makeText(context, "" + jsonArray.size(), Toast.LENGTH_SHORT).show();
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray_terms = new VectorTOJsonConverter().getJsonArrayTerms(terms);
            JsonArray jsonarray_attach = new JsonArray();

            jsonarray_attach.add(attachemnt);

           // JsonArray jsArray2=lst_attach.toArray();


/*
        JsonObject jsonObject_term=new JsonObject();
        jsonObject_term.addProperty("TermId",0);
                jsonObject_term.addProperty("SrNo", 0);
                jsonObject_term.addProperty("OrderId", 0);
                jsonObject_term.addProperty("ParticularId", 2);
                jsonObject_term.addProperty("Condition", "test");
                jsonObject_term.addProperty("IsRemoved", false);
        jsonArray_terms.add(jsonObject_term);*/

            jsonObject.addProperty("OrderId", 0);
            jsonObject.addProperty("OrderTypeId", Integer.parseInt(str_ordertypeid.trim()));
            jsonObject.addProperty("OrderCategoryId", Integer.parseInt(str_ordercategoryid.trim()));
            jsonObject.addProperty("CustomerId", Integer.parseInt(str_customerid.trim()));
            jsonObject.addProperty("DeliveryAddressId", Integer.parseInt(str_deliveryaddressid.trim()));
            jsonObject.addProperty("BillingAddressId", Integer.parseInt(str_billingaddressid.trim()));
            jsonObject.addProperty("DeliveryDate", str_deliverydate + "T00:00:00.930Z");
            jsonObject.addProperty("TransportNote", str_transportnote);
            int s = 2;
            jsonObject.addProperty("Remark", str_remark);
            jsonObject.addProperty("StatusId", 0);
            jsonObject.addProperty("CurrencyId", s);

            jsonObject.add("Attachments", jsonarray_attach);
            jsonObject.addProperty("IsActive", true);
            jsonObject.addProperty("CurrentUser", 2);
            jsonObject.addProperty("CustomerName", "sachin");
            jsonObject.addProperty("SalesPerson", Integer.parseInt(str_saleperson.trim()));
            jsonObject.addProperty("ERPOrderNo", "");
            jsonObject.addProperty("ERPEntryDate", "");
            jsonObject.addProperty("IsThirdParty", isThirdPartyChecked);
            jsonObject.addProperty("ThirdPartyId", str_isthirdpartyid != null ? Integer.parseInt(str_isthirdpartyid.trim()) : 0); //Integer.parseInt(str_isthirdpartyid.trim())
            jsonObject.addProperty("DispatchFrom", Integer.parseInt(str_dispatchfrom.trim()));
            jsonObject.addProperty("OrderBy", Integer.parseInt(str_givenbyid.trim()));
            jsonObject.addProperty("FollowWith", Integer.parseInt(str_followupid.trim()));
            jsonObject.addProperty("CurrentUserCompany", 8);
            jsonObject.add("ProductItems", jsonArray);
            jsonObject.add("TermConditionItems", jsonArray_terms);

            JsonObject jsonObject_appinfo = new JsonObject();
            jsonObject_appinfo.addProperty("Name", "Mobile");
            jsonObject_appinfo.addProperty("Version", "V" + BuildConfig.VERSION_CODE);
            jsonObject.add("AppInfo", jsonObject_appinfo);

            Log.i("Json Data :",jsonObject.toString().trim());

            JsonObject jsonObject_extraPropeties=jsonObject;

            jsonObject_extraPropeties.addProperty("OrderTypeName",jsonObject_local.get("OrderTypeName").toString());
            jsonObject_extraPropeties.addProperty("OrderCategoryName",jsonObject_local.get("OrderCategoryName").toString());
            jsonObject_extraPropeties.addProperty("CustomerName",jsonObject_local.get("CustomerName").toString());
            jsonObject_extraPropeties.addProperty("DeliveryAddressName",jsonObject_local.get("DeliveryAddressName").toString());
            jsonObject_extraPropeties.addProperty("BillingAddressName",jsonObject_local.get("BillingAddressName").toString());
            jsonObject_extraPropeties.addProperty("SalesPersonName",jsonObject_local.get("SalesPersonName").toString());
            jsonObject_extraPropeties.addProperty("DispatchFromName",jsonObject_local.get("DispatchFromName").toString());
            jsonObject_extraPropeties.addProperty("OrderByName",jsonObject_local.get("OrderByName").toString());
            jsonObject_extraPropeties.addProperty("FollowWithName",jsonObject_local.get("FollowWithName").toString());
            jsonObject_extraPropeties.addProperty("StatusName",jsonObject_local.get("StatusName").toString());
            jsonObject_extraPropeties.addProperty("ThirdPartyName",jsonObject_local.get("ThirdPartyName").toString());

            Log.i("Local Json Data :",jsonObject_extraPropeties.toString().trim());

            int localid=new SqlightDatabaseUtil(context).getMaxOfflineOrderID();
            if(new SqlightDatabaseUtil(context).addOrderLocal(""+localid,jsonObject_extraPropeties.toString().trim(),0,jsonObject_local.get("CustomerName").toString()))
            {
                Toast.makeText(context, "Data Added in Local DB ..", Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(context, "Not Added", Toast.LENGTH_SHORT).show();
            }

            try {

                if (!progressDialog.isShowing())
                    progressDialog.show();

                Call<String> call = RetrofitClient.getInstance().getMyApi().addOrder(jsonObject);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        Log.i("Data is ", response.body());

                        if (response.body() == null) {

                        }
                        try {
                            double d = Integer.parseInt(response.body().trim());
                            new SqlightDatabaseUtil(context).updateLocalOrerStatus(""+localid,1);

                        } catch (NumberFormatException nfe) {

                        }


                        resultOutput.onOrderPlaced(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Log.e("Error is", t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.i("Error is", e.getMessage());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateOrder(int Orderid,String OrderERPId,String OrderERPDate,String str_ordertypeid, String str_customerid, String str_givenbyid, String str_followupid, String str_deliveryaddressid, String str_billingaddressid, String str_isthirdpartyid, String str_deliverydate, String str_transportnote, String str_dispatchfrom, String str_saleperson, String str_remark, String str_ordercategoryid, Vector[] items,Vector[] terms,boolean isThirdPartyChecked,String attachemnt) {



       /* JsonArray jsonArray=new VectorTOJsonConverter().getJsonArray(items);
        Toast.makeText(context, ""+jsonArray.size(), Toast.LENGTH_SHORT).show();
        JsonObject jsonObject=new JsonObject();
        JsonArray jsonArray_terms=new JsonArray();

        JsonObject jsonObject_term=new JsonObject();
        jsonObject_term.addProperty("TermId",0);
        jsonObject_term.addProperty("SrNo", 0);
        jsonObject_term.addProperty("OrderId", Orderid);
        jsonObject_term.addProperty("ParticularId", 2);
        jsonObject_term.addProperty("Condition", "test");
        jsonObject_term.addProperty("IsRemoved", false);
        jsonArray_terms.add(jsonObject_term);

        jsonObject.addProperty("OrderId", Orderid);
        jsonObject.addProperty("OrderTypeId", Integer.parseInt(str_ordertypeid.trim()));
        jsonObject.addProperty("OrderCategoryId", Integer.parseInt(str_ordercategoryid.trim()));
        jsonObject.addProperty("CustomerId", Integer.parseInt(str_customerid.trim()));
        jsonObject.addProperty("DeliveryAddressId", Integer.parseInt(str_deliveryaddressid.trim()));
        jsonObject.addProperty("BillingAddressId", Integer.parseInt(str_billingaddressid.trim()));
        jsonObject.addProperty("DeliveryDate", "2022-01-05T17:42:44.930Z");
        jsonObject.addProperty("TransportNote", str_transportnote);
        int  s=2;
        jsonObject.addProperty("Remark", str_remark);
        jsonObject.addProperty("StatusId", 0);
        jsonObject.addProperty("CurrencyId", s);

        jsonObject.addProperty("Attachments","");
        jsonObject.addProperty("IsActive", true);
        jsonObject.addProperty("CurrentUser", 2);
        jsonObject.addProperty("CustomerName","sachin");
        jsonObject.addProperty("SalesPerson", Integer.parseInt(str_saleperson.trim()));
        jsonObject.addProperty("ERPOrderNo", OrderERPId);
        jsonObject.addProperty("ERPEntryDate",OrderERPDate);
        jsonObject.addProperty("IsThirdParty", true);
        jsonObject.addProperty("ThirdPartyId", str_isthirdpartyid!=null?Integer.parseInt(str_isthirdpartyid.trim()):0);
        jsonObject.addProperty("DispatchFrom", Integer.parseInt(str_dispatchfrom.trim()));
        jsonObject.addProperty("OrderBy", s);
        jsonObject.addProperty("FollowWith", Integer.parseInt(str_followupid.trim()));
        jsonObject.addProperty("CurrentUserCompany", 8);
        jsonObject.add("ProductItems",jsonArray);
        jsonObject.add("TermConditionItems",jsonArray_terms);

        JsonObject jsonObject_appinfo=new JsonObject();
        jsonObject_appinfo.addProperty("Name","Mobile");
        jsonObject_appinfo.addProperty("Version", "V "+BuildConfig.VERSION_CODE);
        jsonObject.add("AppInfo",jsonObject_appinfo);

*/

        JsonArray jsonArray=new VectorTOJsonConverter().getJsonArray(items);
        Toast.makeText(context, ""+jsonArray.size(), Toast.LENGTH_SHORT).show();
        JsonObject jsonObject=new JsonObject();
        JsonArray jsonArray_terms=new VectorTOJsonConverter().getJsonArrayTerms(terms);
        JsonArray jsonarray_attach = new JsonArray();

        jsonarray_attach.add(attachemnt);
/*
        JsonObject jsonObject_term=new JsonObject();
        jsonObject_term.addProperty("TermId",0);
                jsonObject_term.addProperty("SrNo", 0);
                jsonObject_term.addProperty("OrderId", 0);
                jsonObject_term.addProperty("ParticularId", 2);
                jsonObject_term.addProperty("Condition", "test");
                jsonObject_term.addProperty("IsRemoved", false);
        jsonArray_terms.add(jsonObject_term);*/

        jsonObject.addProperty("OrderId", Orderid);
        jsonObject.addProperty("OrderTypeId", Integer.parseInt(str_ordertypeid.trim()));
        jsonObject.addProperty("OrderCategoryId", Integer.parseInt(str_ordercategoryid.trim()));
        jsonObject.addProperty("CustomerId", Integer.parseInt(str_customerid.trim()));
        jsonObject.addProperty("DeliveryAddressId", Integer.parseInt(str_deliveryaddressid.trim()));
        jsonObject.addProperty("BillingAddressId", Integer.parseInt(str_billingaddressid.trim()));
        jsonObject.addProperty("DeliveryDate", str_deliverydate+"T00:00:00.930Z");
        jsonObject.addProperty("TransportNote", str_transportnote);
        int  s=2;
        jsonObject.addProperty("Remark", str_remark);
        jsonObject.addProperty("StatusId", 0);
        jsonObject.addProperty("CurrencyId", s);

        jsonObject.add("Attachments", jsonarray_attach);
        jsonObject.addProperty("IsActive", true);
        jsonObject.addProperty("CurrentUser", 2);
        jsonObject.addProperty("CustomerName","sachin");
        jsonObject.addProperty("SalesPerson", Integer.parseInt(str_saleperson.trim()));
        jsonObject.addProperty("ERPOrderNo", "");
        jsonObject.addProperty("ERPEntryDate","");
        jsonObject.addProperty("IsThirdParty", isThirdPartyChecked);
        jsonObject.addProperty("ThirdPartyId",  str_isthirdpartyid!=null?Integer.parseInt(str_isthirdpartyid.trim()):0);
        jsonObject.addProperty("DispatchFrom", Integer.parseInt(str_dispatchfrom.trim()));
        jsonObject.addProperty("OrderBy",  Integer.parseInt(str_givenbyid.trim()));
        jsonObject.addProperty("FollowWith", Integer.parseInt(str_followupid.trim()));
        jsonObject.addProperty("CurrentUserCompany", 8);
        jsonObject.add("ProductItems",jsonArray);
        jsonObject.add("TermConditionItems",jsonArray_terms);

        JsonObject jsonObject_appinfo=new JsonObject();
        jsonObject_appinfo.addProperty("Name","Mobile");
        jsonObject_appinfo.addProperty("Version", "V"+BuildConfig.VERSION_CODE);
        jsonObject.add("AppInfo",jsonObject_appinfo);



        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<String> call = RetrofitClient.getInstance().getMyApi().updateOrder(jsonObject);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.i("Data is ",response.body());
                    resultOutput.onOrderPlaced(response.body());
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

    public void getOrderData(int orderID)
    {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<OrderDetailsModel> call = RetrofitClient.getInstance().getMyApi().getViewOrderDatails(orderID);
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


    public void getDispatchCustomer(JsonObject jsonObject) {
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
                            resultOutput.onListResponce_Dispatch(videos);
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
    public void getThirdPartyCustomer(JsonObject jsonObject) {
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
                            resultOutput.onListResponce_thirdParty(videos);
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
}
