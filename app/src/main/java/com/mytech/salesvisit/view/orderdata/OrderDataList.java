package com.mytech.salesvisit.view.orderdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.adapter.OrderDataAdapter;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.view.ordercreate.OrderCreate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderDataList extends AppCompatActivity implements OrderDataListener {
    OrderDataAPI orderDataAPI;
    Context context;
    JsonObject jsonObject;
    LinearLayoutManager mManager;
    RecyclerView rc_orderdata;
    List<OrderDataModel.OrderData> lst_OrderData=new ArrayList<>();
    OrderDataAdapter orderDataAdapter;
    ImageView imageView_myorder,imageView_teamsorder;
    TextView textView_myoorder,textView_teamsorder;
    int page=3;
    AppDatabase db;
    User user;
    String username;
    int usercode;
    String UserType="OO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = OrderDataList.this;
        setContentView(R.layout.activity_order_data_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Order List");
        rc_orderdata=findViewById(R.id.rc_orderdata);
        imageView_myorder=findViewById(R.id.img_myorder);
                imageView_teamsorder=findViewById(R.id.img_teamorders);
        textView_myoorder=findViewById(R.id.txt_myorder);
                textView_teamsorder=findViewById(R.id.txt_teamorders);

        mManager = new LinearLayoutManager(context);
        rc_orderdata.setLayoutManager(mManager);
        db = AppDatabase.getInstance(getApplicationContext());
        user = db.userDao().getUser();
        usercode=user.getUserID();
        Toast.makeText(context, ""+usercode, Toast.LENGTH_SHORT).show();
        username=user.getFullName();

        orderDataAPI = new OrderDataAPI(context, this);
/*        jsonObject=new JsonObject();

        jsonObject.addProperty("PageIndex", 1);
        jsonObject.addProperty("PageSize", 100);
        JsonObject json = new JsonObject();
        json.addProperty("CurrentUser", 2);
        json.addProperty("UserType", "OO");
        jsonObject.add("EntityFilter", json);
        orderDataAPI.getOrderData(jsonObject);*/
        try{
            page=1;
            jsonObject=new JsonObject();
            jsonObject.addProperty("PageIndex", 1);
            jsonObject.addProperty("PageSize", 10);
            JsonObject json = new JsonObject();
            json.addProperty("CurrentUser", usercode);
            json.addProperty("UserType", UserType);
            jsonObject.add("EntityFilter", json);
            orderDataAPI.getOrderData(jsonObject);
        }catch (Exception e)
        {

        }
        rc_orderdata.setOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
                 if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {

                    // Toast.makeText(context, "Scroll", Toast.LENGTH_SHORT).show();

                     //   loadingPB.setVisibility(View.VISIBLE);
                     // getDataFromAPI(page, limit);
                   //  Toast.makeText(context, "Page Size "+page, Toast.LENGTH_SHORT).show();
                     jsonObject=new JsonObject();
                     jsonObject.addProperty("PageIndex", page);
                     jsonObject.addProperty("PageSize", 10);
                     JsonObject json = new JsonObject();
                     json.addProperty("CurrentUser", usercode);
                     json.addProperty("UserType", UserType);
                     jsonObject.add("EntityFilter", json);
                     orderDataAPI.getOrderData(jsonObject);
                     page+=1;
                 }

                 if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                     //reached top
                     //   Log.i("Checke::","Reached Top");

                 }
                 if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                     //scrolling
                 }
             }
         });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void myorders(View v)
    {
        try{
            rc_orderdata.setAdapter(null);
            page=1;
            jsonObject=new JsonObject();
            UserType="OO";
            jsonObject.addProperty("PageIndex", 1);
            jsonObject.addProperty("PageSize", 10);
            JsonObject json = new JsonObject();
            json.addProperty("CurrentUser", usercode);
            json.addProperty("UserType", UserType);
            jsonObject.add("EntityFilter", json);
            orderDataAPI.getOrderData(jsonObject);
        }catch (Exception e)
        {

        }
    }
    public void teamsorders(View v)
    {

        try{
            page=1;
            rc_orderdata.setAdapter(null);
            jsonObject=new JsonObject();
            UserType="TO";
            jsonObject.addProperty("PageIndex", 1);
            jsonObject.addProperty("PageSize", 10);
            JsonObject json = new JsonObject();
            json.addProperty("CurrentUser", usercode);
            json.addProperty("UserType", UserType);
            jsonObject.add("EntityFilter", json);
            orderDataAPI.getOrderData(jsonObject);
        }catch (Exception e)
        {

        }
    }

    @Override
    public void onResult(String result) {

    }

    @Override
    public void onListResponce(OrderDataModel result) {

       // Toast.makeText(context, "Total : "+ result.getTotal()+" Result"+result.getData().size(), Toast.LENGTH_SHORT).show();

        try{
            if(result.getData()!=null) {
                lst_OrderData.addAll(result.getData());
                //   Toast.makeText(context, ""+lst_OrderData.size(), Toast.LENGTH_SHORT).show();
                //txt_days.setText(videos.size()+" Founds.");
                if (page == 1) {
                    orderDataAdapter = new OrderDataAdapter((ArrayList) lst_OrderData, context, this);
                    rc_orderdata.setAdapter(orderDataAdapter);
                } else {
                    orderDataAdapter.notifyDataSetChanged();
                }
            }else
            {
                Toast.makeText(context, "No more records.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {

        }
    }

    @Override
    public void onRemoveOrder(int orderId) {
        //Toast.makeText(context, ""+orderId, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Remove Order");
        builder.setMessage("Do want to remove order?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderDataAPI.removeOrder(orderId);
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onEditOrder(int orderId) {
        Toast.makeText(context, ""+orderId, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context,OrderCreate.class);
        intent.putExtra("Type","Edit");
        intent.putExtra("oid",""+orderId);
        startActivity(intent);
    }



    @Override
    public void onOrderRemoveResponce(String result) {
        Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
     /*   jsonObject=new JsonObject();

        jsonObject.addProperty("PageIndex", 1);
        jsonObject.addProperty("PageSize", 50);
        JsonObject json = new JsonObject();
        json.addProperty("CurrentUser", 2);
        json.addProperty("UserType", "OO");
        jsonObject.add("EntityFilter", json);
        orderDataAPI.getOrderData(jsonObject);*/
    }

    public void createorder(View view) {
        try{
            Intent intent=new Intent(context, OrderCreate.class);
            intent.putExtra("Type","New");
          //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }catch (Exception e)
        {

        }
    }
}