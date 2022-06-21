package com.mytech.salesvisit.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.mytech.salesvisit.R;
import com.mytech.salesvisit.model.OrderDataModel;
import com.mytech.salesvisit.view.orderdata.OrderDataListener;
import com.mytech.salesvisit.view.vieworderdetails.ViewOrdeDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class OrderDataAdapter extends RecyclerView.Adapter<OrderDataAdapter.DataObjectHolder> {


    Context context;
    private static final int UNSELECTED = -1;
    OrderDataListener orderDataListener;

    ArrayList<OrderDataModel.OrderData> lst_Orderdata = null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }

    public OrderDataAdapter(ArrayList<OrderDataModel.OrderData> lst_Orderdata, Context context,OrderDataListener orderDataListener) {

        this.lst_Orderdata = lst_Orderdata;
        //   Log.i("Action Count:", ">>" + orderData.size());
        this.context = context;
        this.orderDataListener=orderDataListener;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_data, parent, false);

        return new DataObjectHolder(view);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //  if (mSellerProductlist.size() > 0) {
        return lst_Orderdata.size();
        //} else {
        //  return 0;
        // }
    }


    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            OrderDataModel.OrderData dataModel = lst_Orderdata.get(position);

            holder.txt_customername.setText(dataModel.getCustomerName()!=null?dataModel.getCustomerName():"-");
            holder.txt_orderid.setText("" + dataModel.getOrderId()!=null?""+dataModel.getOrderId():"-");
            holder.txt_erp_orderid.setText("" + dataModel.getERPOrderNo()!=null?dataModel.getERPOrderNo():"-");
            holder.txt_orderdate.setText("" + dataModel.getOrderDate()!=null?parseDateToddMMyyyy(dataModel.getOrderDate()):"-");
            holder.txt_deliverydate.setText("" + dataModel.getDeliveryDate().trim()!=null?parseDateToddMMyyyy(dataModel.getDeliveryDate()):"-");
            holder.txt_salesperson.setText("" + dataModel.getSalesPersonName()!=null?dataModel.getSalesPersonName():"-");
            holder.txt_statusname.setText("" + dataModel.getStatusName()!=null?dataModel.getStatusName():"-");
            holder.txt_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, ViewOrdeDetails.class);
                    intent.putExtra("id",""+dataModel.getOrderId());
                    context.startActivity(intent);

                }
            });
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Remove", Toast.LENGTH_SHORT).show();
                    orderDataListener.onRemoveOrder(dataModel.getOrderId());
                }
            });

            holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                    orderDataListener.onEditOrder(dataModel.getOrderId());
                }
            });

        } catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txt_customername;
        TextView txt_orderid, txt_erp_orderid;
        TextView txt_orderdate, txt_deliverydate;
        TextView txt_statusname, txt_salesperson;
ImageView img_delete,img_edit;
TextView txt_details;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_customername = itemView.findViewById(R.id.txt_customername);
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_erp_orderid = itemView.findViewById(R.id.txt_erp_order_id);
            txt_orderdate = itemView.findViewById(R.id.txt_orderdate);
            txt_deliverydate = itemView.findViewById(R.id.txt_deliverydate);
            txt_salesperson = itemView.findViewById(R.id.txt_salesperson);
            txt_statusname = itemView.findViewById(R.id.txt_statusname);
            txt_details = itemView.findViewById(R.id.txt_details);
            img_delete= itemView.findViewById(R.id.img_delete);
            img_edit= itemView.findViewById(R.id.img_edit);

        }
    }

    public String parseDateToddMMyyyy(String time) {
        time=time.replace("T"," ");
        String inputPattern = "yyyy-MM-dd HH:mm:ss"; //2022-06-07T00:00:00
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
