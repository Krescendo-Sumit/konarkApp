package com.mytech.salesvisit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.model.CustomerModel;
import com.mytech.salesvisit.model.ProductModel;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.DataObjectHolder> {


    Context context;
    private static final int UNSELECTED = -1;
    EventListener eventListener;

    ArrayList<ProductModel> lst_Orderdata = null;

    public interface EventListener {
        void  onProductSelected(ProductModel productModel);
    }

    public ProductAdapter(ArrayList<ProductModel> lst_Orderdata, Context context, EventListener eventListener) {

        this.lst_Orderdata = lst_Orderdata;
        //   Log.i("Action Count:", ">>" + orderData.size());
        this.context = context;
        this.eventListener=eventListener;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);

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
            ProductModel dataModel = lst_Orderdata.get(position);

            holder.txt_customername.setText(dataModel.getText()!=null?dataModel.getText():"-");
            holder.txt_custid.setText("" + dataModel.getId()!=null?""+dataModel.getId():"-");
            if(dataModel.getId().trim().equals("0"))
                holder.txt_custid.setText("");
            holder.txt_customername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 eventListener.onProductSelected(dataModel);

                }
            });


        } catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txt_customername;
        TextView txt_custid;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_customername = itemView.findViewById(R.id.txt_customername);
            txt_custid = itemView.findViewById(R.id.txtcustid);


        }
    }


}
