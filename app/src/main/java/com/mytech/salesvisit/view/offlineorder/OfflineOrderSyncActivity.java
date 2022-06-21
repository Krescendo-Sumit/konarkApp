package com.mytech.salesvisit.view.offlineorder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.OrderDetailsModel;
import com.mytech.salesvisit.model.ProductModel;
import com.mytech.salesvisit.util.RetrofitClient;
import com.mytech.salesvisit.util.SqlightDatabaseUtil;
import com.mytech.salesvisit.view.ordercreate.OrderCreateAPI;
import com.mytech.salesvisit.view.ordercreate.ResultOutput;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineOrderSyncActivity extends AppCompatActivity {
     SqlightDatabaseUtil sqlightDatabaseUtil;
     Vector vector[];
     TableLayout tableLayout;
     Context context;
     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_order_sync);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=OfflineOrderSyncActivity.this;
        setTitle("Pending Orders");
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait....");
        sqlightDatabaseUtil=new SqlightDatabaseUtil(context);
        tableLayout=findViewById(R.id.tbl_grid);
        loadOrders();

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
    private void loadOrders() {
        try{
            tableLayout.removeAllViews();
            TableRow row_title=new TableRow(context);
            TextView title_custname=new TextView(context);
            title_custname.setText("Customer Name ");
            TextView title_date=new TextView(context);
            title_date.setText("Created Date ");
            TextView title_button=new TextView(context);
            title_button.setText("");

            title_custname.setPadding(2,2,2,2);
            title_button.setPadding(2,2,2,2);
            title_date.setPadding(2,2,2,2);

            title_custname.setTextColor(Color.BLACK);
            title_button.setTextColor(Color.BLACK);
            title_date.setTextColor(Color.BLACK);

           title_custname.setBackgroundResource(R.drawable.shape);
            title_button.setBackgroundResource(R.drawable.shape);
            title_date.setBackgroundResource(R.drawable.shape);


            row_title.addView(title_custname);
            row_title.addView(title_date);
            row_title.addView(title_button);
            tableLayout.addView(row_title);


            vector=sqlightDatabaseUtil.getAllOfflineOrders();
            for(int i=0;i<vector.length;i++)
            {
                TableRow row=new TableRow(context);
                Log.i("Values ",""+vector[i].elementAt(1).toString());
                TextView text_id=new TextView(context);
                text_id.setPadding(2,2,2,2);
                text_id.setText(""+vector[i].elementAt(3).toString().replace("\"",""));
                text_id.setBackgroundResource(R.drawable.shape);
                TextView text_createddate=new TextView(context);
                text_createddate.setPadding(2,2,2,2);
                text_createddate.setText(""+vector[i].elementAt(4).toString());
                text_createddate.setBackgroundResource(R.drawable.shape);
                TextView btn_sync=new TextView(context);
                btn_sync.setPadding(2,2,2,2);
                btn_sync.setText("Sync");
                btn_sync.setTextColor(Color.RED);
                btn_sync.setBackgroundResource(R.drawable.shape);
                btn_sync.setId(Integer.parseInt(vector[i].elementAt(0).toString().trim()));
                btn_sync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id=view.getId();

                        Vector v=sqlightDatabaseUtil.getAllOfflineOrdersById(id);
                        JsonObject jsonObject=new JsonParser().parse(v.elementAt(1).toString().trim()).getAsJsonObject();
                        syncData(jsonObject,id);
                        Log.i("JsonData",jsonObject.toString());
                    }
                });



                row.addView(text_id);
                row.addView(text_createddate);
                row.addView(btn_sync);

                tableLayout.addView(row);



            }

        }catch(Exception e)
        {

        }
    }




    public void syncData(JsonObject jsonObject ,int id)
    {
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
                    sqlightDatabaseUtil.updateLocalOrerStatus(""+id,1);
           loadOrders();
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
    }

}