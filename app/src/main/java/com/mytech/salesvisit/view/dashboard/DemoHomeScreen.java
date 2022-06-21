package com.mytech.salesvisit.view.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.view.ordercreate.OrderCreate;
import com.mytech.salesvisit.view.orderdata.OrderDataList;

public class DemoHomeScreen extends AppCompatActivity {
    TextView txt_create_order, txt_view_my_orders;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_home_screen);
        context = DemoHomeScreen.this;
        init();
    }

    public void init() {
        txt_create_order = findViewById(R.id.txt_create_order);
        txt_view_my_orders = findViewById(R.id.txt_view_myorders);

        txt_create_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderCreate.class);
                intent.putExtra("Type","New");
                startActivity(intent);
            }
        });

        txt_view_my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDataList.class);
                startActivity(intent);
            }
        });
    }
}